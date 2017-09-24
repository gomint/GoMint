/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import com.koloboke.collect.LongCursor;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;
import io.gomint.entity.Entity;
import io.gomint.event.player.PlayerKickEvent;
import io.gomint.event.player.PlayerQuitEvent;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.PacketReliability;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.handler.*;
import io.gomint.server.network.packet.*;
import io.gomint.server.util.BatchUtil;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.Pair;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.WorldAdapter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.InflaterInputStream;

import static io.gomint.server.network.Protocol.*;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PlayerConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger( PlayerConnection.class );
    private static final Map<Class<? extends Packet>, PacketHandler> PACKET_HANDLERS = new HashMap<>();

    static {
        // Register all packet handlers we need
        PACKET_HANDLERS.put( PacketMovePlayer.class, new PacketMovePlayerHandler() );
        PACKET_HANDLERS.put( PacketSetChunkRadius.class, new PacketSetChunkRadiusHandler() );
        PACKET_HANDLERS.put( PacketPlayerAction.class, new PacketPlayerActionHandler() );
        PACKET_HANDLERS.put( PacketRemoveBlock.class, new PacketRemoveBlockHandler() );
        PACKET_HANDLERS.put( PacketMobArmorEquipment.class, new PacketMobArmorEquipmentHandler() );
        PACKET_HANDLERS.put( PacketAdventureSettings.class, new PacketAdventureSettingsHandler() );
        PACKET_HANDLERS.put( PacketResourcePackResponse.class, new PacketResourcePackResponseHandler() );
        PACKET_HANDLERS.put( PacketCraftingEvent.class, new PacketCraftingEventHandler() );
        PACKET_HANDLERS.put( PacketLogin.class, new PacketLoginHandler() );
        PACKET_HANDLERS.put( PacketMobEquipment.class, new PacketMobEquipmentHandler() );
        PACKET_HANDLERS.put( PacketInteract.class, new PacketInteractHandler() );
        PACKET_HANDLERS.put( PacketEncryptionResponse.class, new PacketEncryptionResponseHandler() );
        PACKET_HANDLERS.put( PacketInventoryTransaction.class, new PacketInventoryTransactionHandler() );
        PACKET_HANDLERS.put( PacketContainerOpen.class, new PacketContainerOpenHandler() );
        PACKET_HANDLERS.put( PacketContainerClose.class, new PacketContainerCloseHandler() );
        PACKET_HANDLERS.put( PacketHotbar.class, new PacketHotbarHandler() );
        PACKET_HANDLERS.put( PacketText.class, new PacketTextHandler() );
        PACKET_HANDLERS.put( PacketCommandRequest.class, new PacketCommandRequestHandler() );
        PACKET_HANDLERS.put( PacketWorldSoundEvent.class, new PacketWorldSoundEventHandler() );
    }

    // Network manager that created this connection:
    @Getter private final NetworkManager networkManager;
    @Getter @Setter private EncryptionHandler encryptionHandler;
    @Getter private final GoMintServer server;

    // Actual connection for wire transfer:
    @Getter
    private final Connection connection;

    // World data
    private final LongSet playerChunks;

    // Connection State:
    @Getter @Setter private PlayerConnectionState state;
    private int sentChunks;
    private BlockingQueue<Packet> sendQueue;
    @Setter private int neededChunksSent;

    // Entity
    @Getter @Setter private EntityPlayer entity;
    private LongSet currentlySendingPlayerChunks;

    /**
     * Constructs a new player connection.
     *
     * @param networkManager The network manager creating this instance
     * @param connection     The jRakNet connection for actual wire-transfer
     * @param initialState   The player connection's initial state
     */
    PlayerConnection( NetworkManager networkManager, Connection connection, PlayerConnectionState initialState ) {
        this.networkManager = networkManager;
        this.connection = connection;
        this.state = initialState;
        this.server = networkManager.getServer();

        this.playerChunks = HashLongSets.newMutableSet();
        this.currentlySendingPlayerChunks = HashLongSets.newMutableSet();
    }

    /**
     * Add a packet to the queue to be batched in the next tick
     *
     * @param packet The packet which should be queued
     */
    public void addToSendQueue( Packet packet ) {
        if ( this.sendQueue == null ) {
            this.sendQueue = new LinkedBlockingQueue<>();
        }

        this.sendQueue.offer( packet );
    }

    /**
     * Notifies the player connection that the player's view distance was changed somehow. This might
     * result in several packets and chunks to be sent in order to account for the change.
     */
    public void onViewDistanceChanged() {
        LOGGER.debug( "View distance changed to " + this.getEntity().getViewDistance() );
        this.checkForNewChunks( null );
        this.sendChunkRadiusUpdate();
    }

    /**
     * Performs a network tick on this player connection. All incoming packets are received and handled
     * accordingly.
     *
     * @param currentMillis Time when the tick started
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    public void update( long currentMillis, float dT ) {
        // Receive all waiting packets:
        EncapsulatedPacket packetData;
        while ( ( packetData = this.connection.receive() ) != null ) {
            this.handleSocketData( currentMillis, new PacketBuffer( packetData.getPacketData(), 0 ), false );
        }

        // Check if we need to send chunks
        if ( this.entity != null ) {
            if ( this.entity.getChunkSendQueue().size() > 0 ) {
                int currentX = CoordinateUtils.fromBlockToChunk( (int) this.entity.getPositionX() );
                int currentZ = CoordinateUtils.fromBlockToChunk( (int) this.entity.getPositionZ() );

                // Check if we have a slot
                Queue<ChunkAdapter> queue = this.entity.getChunkSendQueue();
                int alreadySent = 0;
                while ( queue.size() > 0 && alreadySent < 19 ) {
                    ChunkAdapter chunk = queue.poll();
                    if ( chunk == null ) continue;

                    if ( Math.abs( chunk.getX() - currentX ) > this.entity.getViewDistance() ||
                            Math.abs( chunk.getZ() - currentZ ) > this.entity.getViewDistance() ) {
                        continue;
                    }

                    this.sendWorldChunk( CoordinateUtils.toLong( chunk.getX(), chunk.getZ() ), chunk.getCachedPacket() );

                    // Send all spawned entities
                    Collection<Entity> entities = chunk.getEntities();
                    if ( entities != null ) {
                        for ( io.gomint.entity.Entity entity : entities ) {
                            if ( entity instanceof io.gomint.server.entity.Entity ) {
                                this.addToSendQueue( ( (io.gomint.server.entity.Entity) entity ).createSpawnPacket() );
                            }
                        }
                    }

                    alreadySent++;
                }
            }
        }

        // Send all queued packets
        if ( this.sendQueue != null && this.sendQueue.size() > 0 ) {
            PacketBatch batch = BatchUtil.batch( ( this.state != PlayerConnectionState.ENCRPYTION_INIT ) ? this.encryptionHandler : null, this.sendQueue );

            if ( connection.isConnected() ) {
                send( batch );
            }

            this.sendQueue.clear();
        }
    }

    /**
     * Sends the given packet to the player.
     *
     * @param packet The packet which should be send to the player
     */
    public void send( Packet packet ) {
        if ( !( packet instanceof PacketBatch ) ) {
            this.send( BatchUtil.batch( ( this.state != PlayerConnectionState.ENCRPYTION_INIT ) ? this.encryptionHandler : null, packet ) );
        } else {
            PacketBuffer buffer = new PacketBuffer( 64 );
            buffer.writeByte( packet.getId() );
            packet.serialize( buffer );

            this.connection.send( PacketReliability.RELIABLE_ORDERED, packet.orderingChannel(), buffer.getBuffer(), 0, buffer.getPosition() );
        }
    }

    /**
     * Sends the given packet to the player.
     *
     * @param reliability     The reliability to send the packet with
     * @param orderingChannel The ordering channel to send the packet on
     * @param packet          The packet to send to the player
     */
    public void send( PacketReliability reliability, int orderingChannel, Packet packet ) {
        if ( !( packet instanceof PacketBatch ) ) {
            this.send( reliability, orderingChannel, BatchUtil.batch( ( this.state != PlayerConnectionState.ENCRPYTION_INIT ) ? this.encryptionHandler : null, packet ) );
        } else {
            PacketBuffer buffer = new PacketBuffer( 64 );
            buffer.writeByte( packet.getId() );
            packet.serialize( buffer );

            this.connection.send( reliability, orderingChannel, buffer.getBuffer(), 0, buffer.getPosition() );
        }
    }

    /**
     * Sends a world chunk to the player. This is used by world adapters in order to give the player connection
     * a chance to know once it is ready for spawning.
     *
     * @param chunkHash The hash of the chunk to keep track of what the player has loaded
     * @param chunkData The chunk data packet to send to the player
     */
    public void sendWorldChunk( long chunkHash, PacketBatch chunkData ) {
        PacketBatch batch = new PacketBatch();
        batch.setPayload( this.encryptionHandler.encryptInputForClient( chunkData.getPayload() ) );
        this.send( batch );

        synchronized ( this.playerChunks ) {
            this.currentlySendingPlayerChunks.removeLong( chunkHash );
            this.playerChunks.add( chunkHash );
        }

        if ( this.state == PlayerConnectionState.LOGIN ) {
            this.sentChunks++;
            if ( this.sentChunks >= this.neededChunksSent ) {
                int spawnXChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getX() );
                int spawnZChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getZ() );

                WorldAdapter worldAdapter = this.entity.getWorld();
                worldAdapter.movePlayerToChunk( spawnXChunk, spawnZChunk, this.entity );

                this.getEntity().fullyInit();

                this.state = PlayerConnectionState.PLAYING;

                // Send missing chunks to fill view distance
                this.checkForNewChunks( null );
            }
        }
    }

    // ========================================= PACKET HANDLERS ========================================= //

    /**
     * Handles data received directly from the player's connection.
     *
     * @param currentTimeMillis The time in millis of this tick
     * @param buffer            The buffer containing the received data
     * @param batch             Does this packet come out of a batch
     */
    private void handleSocketData( long currentTimeMillis, PacketBuffer buffer, boolean batch ) {
        if ( buffer.getRemaining() <= 0 ) {
            // Malformed packet:
            return;
        }

        // Grab the packet ID from the packet's data
        byte packetId = buffer.readByte();

        // There is some data behind the packet id when non batched packets (2 bytes)
        // TODO: Find out if MCPE uses triads as packetnumbers now
        if ( packetId != PACKET_BATCH ) {
            buffer.readShort();
        }

        // LOGGER.debug( "Got packet with ID: " + Integer.toHexString( packetId & 0xff ) );

        // If we are still in handshake we only accept certain packets:
        if ( this.state == PlayerConnectionState.HANDSHAKE ) {
            if ( packetId == PACKET_BATCH ) {
                this.handleBatchPacket( currentTimeMillis, buffer, batch );
            } else if ( packetId == PACKET_LOGIN ) {
                PacketLogin packet = new PacketLogin();
                packet.deserialize( buffer );
                this.handlePacket( currentTimeMillis, packet );
            } else {
                LOGGER.error( "Received odd packet" );
            }

            // Don't allow for any other packets if we are in HANDSHAKE state:
            return;
        }

        // When we are in encryption init state
        if ( this.state == PlayerConnectionState.ENCRPYTION_INIT ) {
            if ( packetId == PACKET_BATCH ) {
                this.handleBatchPacket( currentTimeMillis, buffer, batch );
            } else if ( packetId == PACKET_ENCRYPTION_RESPONSE ) {
                this.handlePacket( currentTimeMillis, new PacketEncryptionResponse() );
            } else {
                LOGGER.error( "Received odd packet" );
            }

            // Don't allow for any other packets if we are in RESOURCE_PACK state:
            return;
        }

        // When we are in resource pack state
        if ( this.state == PlayerConnectionState.RESOURCE_PACK ) {
            if ( packetId == PACKET_BATCH ) {
                this.handleBatchPacket( currentTimeMillis, buffer, batch );
            } else if ( packetId == PACKET_RESOURCEPACK_RESPONSE ) {
                PacketResourcePackResponse packet = new PacketResourcePackResponse();
                packet.deserialize( buffer );
                this.handlePacket( currentTimeMillis, packet );
            } else {
                LOGGER.error( "Received odd packet" );
            }

            // Don't allow for any other packets if we are in RESOURCE_PACK state:
            return;
        }

        if ( packetId == PACKET_BATCH ) {
            this.handleBatchPacket( currentTimeMillis, buffer, batch );
        } else {
            Packet packet = Protocol.createPacket( packetId );
            if ( packet == null ) {
                this.networkManager.notifyUnknownPacket( packetId, buffer );

                // Got to skip
                buffer.skip( buffer.getRemaining() );
                return;
            }

            packet.deserialize( buffer );
            this.handlePacket( currentTimeMillis, packet );
        }
    }

    /**
     * Handles compressed batch packets directly by decoding their payload.
     *
     * @param buffer The buffer containing the batch packet's data (except packet ID)
     */
    private void handleBatchPacket( long currentTimeMillis, PacketBuffer buffer, boolean batch ) {
        if ( batch ) {
            LOGGER.error( "Malformed batch packet payload: Batch packets are not allowed to contain further batch packets" );
            return;
        }

        // Encrypted?
        byte[] input = new byte[buffer.getRemaining()];
        System.arraycopy( buffer.getBuffer(), buffer.getPosition(), input, 0, input.length );
        if ( this.encryptionHandler != null ) {
            input = this.encryptionHandler.decryptInputFromClient( input );
            if ( input == null ) {
                // Decryption error
                disconnect( "Checksum of encrypted packet was wrong" );
                return;
            }
        }

        InflaterInputStream inflaterInputStream = new InflaterInputStream( new ByteArrayInputStream( input ) );

        ByteArrayOutputStream bout = new ByteArrayOutputStream( buffer.getRemaining() );
        byte[] batchIntermediate = new byte[256];

        try {
            int read;
            while ( ( read = inflaterInputStream.read( batchIntermediate ) ) > -1 ) {
                bout.write( batchIntermediate, 0, read );
            }
        } catch ( IOException e ) {
            LOGGER.error( "Failed to decompress batch packet", e );
            return;
        }

        byte[] payload = bout.toByteArray();

        PacketBuffer payloadBuffer = new PacketBuffer( payload, 0 );
        while ( payloadBuffer.getRemaining() > 0 ) {
            int packetLength = payloadBuffer.readUnsignedVarInt();

            byte[] payData = new byte[packetLength];
            payloadBuffer.readBytes( payData );
            PacketBuffer pktBuf = new PacketBuffer( payData, 0 );
            this.handleSocketData( currentTimeMillis, pktBuf, true );

            if ( pktBuf.getRemaining() > 0 ) {
                LOGGER.error( "Malformed batch packet payload: Could not read enclosed packet data correctly: 0x" + Integer.toHexString( payData[0] ) + " reamining " + pktBuf.getRemaining() + " bytes" );
                return;
            }
        }
    }

    /**
     * Handles a deserialized packet by dispatching it to the appropriate handler method.
     *
     * @param currentTimeMillis The time this packet arrived at the network manager
     * @param packet            The packet to handle
     */
    private void handlePacket( long currentTimeMillis, Packet packet ) {
        PacketHandler handler = PACKET_HANDLERS.get( packet.getClass() );
        if ( handler != null ) {
            handler.handle( packet, currentTimeMillis, this );
            return;
        }

        LOGGER.warn( "No handler for " + packet );
    }

    /**
     * Check if we need to send new chunks to the player
     *
     * @param from which location the entity moved
     */
    public void checkForNewChunks( Location from ) {
        // Don't check until we are fully spawned
        if ( this.state != PlayerConnectionState.PLAYING ) {
            return;
        }

        WorldAdapter worldAdapter = this.entity.getWorld();

        int currentXChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getX() );
        int currentZChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getZ() );

        int viewDistance = this.entity.getViewDistance();
        synchronized ( this.playerChunks ) {
            List<Pair<Integer, Integer>> toSendChunks = new ArrayList<>();
            for ( int sendXChunk = currentXChunk - viewDistance; sendXChunk < currentXChunk + viewDistance; sendXChunk++ ) {
                for ( int sendZChunk = currentZChunk - viewDistance; sendZChunk < currentZChunk + viewDistance; sendZChunk++ ) {
                    toSendChunks.add( new Pair<>( sendXChunk, sendZChunk ) );
                }
            }

            toSendChunks.sort( new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare( Pair<Integer, Integer> o1, Pair<Integer, Integer> o2 ) {
                    if ( Objects.equals( o1.getFirst(), o2.getFirst() ) &&
                            Objects.equals( o1.getSecond(), o2.getSecond() ) ) {
                        return 0;
                    }

                    int distXFirst = Math.abs( o1.getFirst() - currentXChunk );
                    int distXSecond = Math.abs( o2.getFirst() - currentXChunk );

                    int distZFirst = Math.abs( o1.getSecond() - currentZChunk );
                    int distZSecond = Math.abs( o2.getSecond() - currentZChunk );

                    if ( distXFirst + distZFirst > distXSecond + distZSecond ) {
                        return 1;
                    } else if ( distXFirst + distZFirst < distXSecond + distZSecond ) {
                        return -1;
                    }

                    return 0;
                }
            } );

            for ( Pair<Integer, Integer> chunk : toSendChunks ) {
                long hash = CoordinateUtils.toLong( chunk.getFirst(), chunk.getSecond() );

                if ( !this.playerChunks.contains( hash ) &&
                        !this.currentlySendingPlayerChunks.contains( hash ) ) {
                    this.currentlySendingPlayerChunks.add( hash );
                    worldAdapter.sendChunk( chunk.getFirst(), chunk.getSecond(), this.entity, false );
                }
            }
        }

        // Move the player to this chunk
        if ( from != null ) {
            int oldChunkX = CoordinateUtils.fromBlockToChunk( (int) from.getX() );
            int oldChunkZ = CoordinateUtils.fromBlockToChunk( (int) from.getZ() );
            if ( !from.getWorld().equals( worldAdapter ) || oldChunkX != currentXChunk || oldChunkZ != currentZChunk ) {
                worldAdapter.movePlayerToChunk( currentXChunk, currentZChunk, this.entity );
            }
        }

        // Check for unloading chunks
        synchronized ( this.playerChunks ) {
            LongCursor longCursor = this.playerChunks.cursor();
            while ( longCursor.moveNext() ) {
                int x = (int) ( longCursor.elem() >> 32 );
                int z = (int) ( longCursor.elem() ) + Integer.MIN_VALUE;

                if ( x > currentXChunk + viewDistance ||
                        x < currentXChunk - viewDistance ||
                        z > currentZChunk + viewDistance ||
                        z < currentZChunk - viewDistance ) {
                    // TODO: Check for Packets to send to the client to unload the chunk?
                    longCursor.remove();
                }
            }
        }
    }

    public void sendResourcePacks() {
        // We have the chance of forcing resource and behaviour packs here
        PacketResourcePacksInfo packetResourcePacksInfo = new PacketResourcePacksInfo();
        this.send( packetResourcePacksInfo );
    }

    public void sendCommandsEnabled() {
        /*PacketSetCommandsEnabled packetSetCommandsEnabled = new PacketSetCommandsEnabled();
        packetSetCommandsEnabled.setEnabled( false );   // TODO: Change after command system is there
        this.send( packetSetCommandsEnabled );*/
    }

    public void sendDifficulty() {
        PacketSetDifficulty packetSetDifficulty = new PacketSetDifficulty();
        packetSetDifficulty.setDifficulty( 1 );
        this.send( packetSetDifficulty );
    }

    public void sendChunkRadiusUpdate() {
        PacketConfirmChunkRadius packetConfirmChunkRadius = new PacketConfirmChunkRadius();
        packetConfirmChunkRadius.setChunkRadius( this.entity.getViewDistance() );
        this.send( packetConfirmChunkRadius );
    }

    /**
     * Disconnect (kick) the player with a custom message
     *
     * @param message The message with which the player is going to be kicked
     */
    public void disconnect( String message ) {
        if ( this.connection.isConnected() && !this.connection.isDisconnecting() ) {
            this.networkManager.getServer().getPluginManager().callEvent( new PlayerKickEvent( this.entity, message ) );

            if ( message != null && message.length() > 0 ) {
                PacketDisconnect packet = new PacketDisconnect();
                packet.setMessage( message );
                this.send( packet );
            }

            if ( this.entity != null ) {
                LOGGER.info( "Player " + this.entity.getName() + " left the game: " + message );
            } else {
                LOGGER.info( "Player has been disconnected whilst logging in: " + message );
            }

            this.connection.disconnect( message );
        }
    }

    // ====================================== PACKET SENDERS ====================================== //

    /**
     * Sends a PacketPlayState with the specified state to this player.
     *
     * @param state The state to send
     */
    public void sendPlayState( PacketPlayState.PlayState state ) {
        PacketPlayState packet = new PacketPlayState();
        packet.setState( state );
        this.send( packet );
    }

    /**
     * Sends the player a move player packet which will teleport him to the
     * given location.
     *
     * @param location The location to teleport the player to
     */
    public void sendMovePlayer( Location location ) {
        PacketMovePlayer move = new PacketMovePlayer();
        move.setEntityId( this.entity.getEntityId() );
        move.setX( location.getX() );
        move.setY( (float) ( location.getY() + 1.62 ) );
        move.setZ( location.getZ() );
        move.setYaw( location.getYaw() );
        move.setPitch( location.getPitch() );
        move.setMode( (byte) 1 );
        move.setOnGround( this.getEntity().isOnGround() );
        move.setRidingEntityId( 0 );    // TODO: Implement riding entities correctly
        this.send( move );
    }

    /**
     * Sends the player the specified time as world time. The original client sends
     * the current world time every 256 ticks in order to synchronize all client's world
     * times.
     *
     * @param ticks The current number of ticks of the world time
     */
    public void sendWorldTime( int ticks ) {
        PacketWorldTime time = new PacketWorldTime();
        time.setTicks( ticks );
        this.send( time );
    }

    /**
     * Sends a world initialization packet of the world the entity associated with this
     * connection is currently in to this player.
     */
    public void sendWorldInitialization() {
        WorldAdapter world = this.entity.getWorld();

        PacketStartGame packet = new PacketStartGame();
        packet.setEntityId( this.entity.getEntityId() );
        packet.setRuntimeEntityId( this.entity.getEntityId() );
        packet.setGamemode( EnumConnectors.GAMEMODE_CONNECTOR.convert( this.entity.getGamemode() ).getMagicNumber() );
        packet.setSpawn( world.getSpawnLocation().add( 0, 1.62f, 0 ) );
        packet.setX( (int) world.getSpawnLocation().getX() );
        packet.setY( (int) ( world.getSpawnLocation().getY() + 1.62 ) );
        packet.setZ( (int) world.getSpawnLocation().getZ() );
        packet.setWorldGamemode( 0 );
        packet.setDimension( 0 );
        packet.setSeed( 12345 );
        packet.setGenerator( 1 );
        packet.setDifficulty( 1 );
        packet.setLevelId( Base64.getEncoder().encodeToString( world.getWorldName().getBytes() ) );
        packet.setWorldName( world.getWorldName() );
        packet.setTemplateName( "" );
        packet.setGamerules( world.getGamerules() );
        packet.setTexturePacksRequired( true );
        packet.setCommandsEnabled( true );

        this.entity.setPosition( world.getSpawnLocation() );
        this.send( packet );
    }

    /**
     * The underlying RakNet Connection closed. Cleanup
     */
    void close() {
        if ( this.entity != null && this.entity.getWorld() != null ) {
            this.networkManager.getServer().getPluginManager().callEvent( new PlayerQuitEvent( this.entity ) );
            this.entity.getWorld().removePlayer( this.entity );
            this.entity.cleanup();
            this.entity.despawn();
            this.entity = null;
        }
    }

    /**
     * Clear the chunks which we know the player has gotten
     */
    public void resetPlayerChunks() {
        this.playerChunks.clear();
    }

}
