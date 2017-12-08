/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import com.koloboke.collect.LongCursor;
import io.gomint.event.player.PlayerCleanedupEvent;
import io.gomint.event.player.PlayerKickEvent;
import io.gomint.event.player.PlayerQuitEvent;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.PacketReliability;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.handler.*;
import io.gomint.server.network.packet.*;
import io.gomint.server.network.tcp.ConnectionHandler;
import io.gomint.server.network.tcp.protocol.WrappedMCPEPacket;
import io.gomint.server.player.DeviceInfo;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.Pair;
import io.gomint.server.util.StringUtil;
import io.gomint.server.util.Values;
import io.gomint.server.util.collection.ChunkHashSet;
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
    private static final PacketHandler[] PACKET_HANDLERS = new PacketHandler[256];

    static {
        // Register all packet handlers we need
        PACKET_HANDLERS[Protocol.PACKET_MOVE_PLAYER & 0xff] = new PacketMovePlayerHandler();
        PACKET_HANDLERS[Protocol.PACKET_SET_CHUNK_RADIUS & 0xff] = new PacketSetChunkRadiusHandler();
        PACKET_HANDLERS[Protocol.PACKET_PLAYER_ACTION & 0xff] = new PacketPlayerActionHandler();
        PACKET_HANDLERS[Protocol.PACKET_MOB_ARMOR_EQUIPMENT & 0xff] = new PacketMobArmorEquipmentHandler();
        PACKET_HANDLERS[Protocol.PACKET_ADVENTURE_SETTINGS & 0xff] = new PacketAdventureSettingsHandler();
        PACKET_HANDLERS[Protocol.PACKET_RESOURCEPACK_RESPONSE & 0xff] = new PacketResourcePackResponseHandler();
        PACKET_HANDLERS[Protocol.PACKET_CRAFTING_EVENT & 0xff] = new PacketCraftingEventHandler();
        PACKET_HANDLERS[Protocol.PACKET_LOGIN & 0xff] = new PacketLoginHandler();
        PACKET_HANDLERS[Protocol.PACKET_MOB_EQUIPMENT & 0xff] = new PacketMobEquipmentHandler();
        PACKET_HANDLERS[Protocol.PACKET_INTERACT & 0xff] = new PacketInteractHandler();
        PACKET_HANDLERS[Protocol.PACKET_ENCRYPTION_RESPONSE & 0xff] = new PacketEncryptionResponseHandler();
        PACKET_HANDLERS[Protocol.PACKET_INVENTORY_TRANSACTION & 0xff] = new PacketInventoryTransactionHandler();
        PACKET_HANDLERS[Protocol.PACKET_CONTAINER_CLOSE & 0xff] = new PacketContainerCloseHandler();
        PACKET_HANDLERS[Protocol.PACKET_HOTBAR & 0xff] = new PacketHotbarHandler();
        PACKET_HANDLERS[Protocol.PACKET_TEXT & 0xff] = new PacketTextHandler();
        PACKET_HANDLERS[Protocol.PACKET_COMMAND_REQUEST & 0xff] = new PacketCommandRequestHandler();
        PACKET_HANDLERS[Protocol.PACKET_WORLD_SOUND_EVENT & 0xff] = new PacketWorldSoundEventHandler();
        PACKET_HANDLERS[Protocol.PACKET_ANIMATE & 0xff] = new PacketAnimateHandler();
        PACKET_HANDLERS[Protocol.PACKET_ENTITY_EVENT & 0xff] = new PacketEntityEventHandler();
        PACKET_HANDLERS[Protocol.PACKET_MODAL_RESPONSE & 0xFF] = new PacketModalResponseHandler();
        PACKET_HANDLERS[Protocol.PACKET_SERVER_SETTINGS_REQUEST & 0xFF] = new PacketServerSettingsRequestHandler();
    }

    // Network manager that created this connection:
    @Getter
    private final NetworkManager networkManager;
    @Getter
    @Setter
    private EncryptionHandler encryptionHandler;
    @Getter
    private GoMintServer server;

    // Actual connection for wire transfer:
    @Getter
    private final Connection connection;
    @Getter
    private final ConnectionHandler connectionHandler;
    @Setter
    private long tcpId;
    @Setter
    private int tcpPing;

    // World data
    @Getter
    private final ChunkHashSet playerChunks;

    // Connection State:
    @Getter
    @Setter
    private PlayerConnectionState state;
    private int sentChunks;
    private BlockingQueue<Packet> sendQueue;

    // Entity
    @Getter
    @Setter
    private EntityPlayer entity;
    private long sentInClientTick;

    // Additional data
    @Getter
    @Setter
    private DeviceInfo deviceInfo;
    private float lastUpdateDT = 0;

    /**
     * Constructs a new player connection.
     *
     * @param networkManager The network manager creating this instance
     * @param connection     The jRakNet connection for actual wire-transfer
     * @param tcpConnection  TCP connection for low latency communication with proxies
     * @param initialState   The player connection's initial state
     */
    PlayerConnection( NetworkManager networkManager, Connection connection, ConnectionHandler tcpConnection, PlayerConnectionState initialState ) {
        this.networkManager = networkManager;
        this.connection = connection;
        this.connectionHandler = tcpConnection;
        this.state = initialState;
        this.server = networkManager.getServer();
        this.playerChunks = ChunkHashSet.withExpectedSize( 100 );

        // Attach data processor if needed
        if ( this.connection != null ) {
            this.connection.addDataProcessor( packetData -> {
                PacketBuffer buffer = new PacketBuffer( packetData.getPacketData(), 0 );
                if ( buffer.getRemaining() <= 0 ) {
                    // Malformed packet:
                    return packetData;
                }

                // Check if packet is batched
                byte packetId = buffer.readByte();
                if ( packetId == Protocol.PACKET_BATCH ) {
                    // Decompress and decrypt
                    byte[] pureData = handleBatchPacket( buffer );
                    EncapsulatedPacket newPacket = new EncapsulatedPacket();
                    newPacket.setPacketData( pureData );
                    return newPacket;
                }

                return packetData;
            } );
        }
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

        if ( !this.sendQueue.offer( packet ) ) {
            LOGGER.warn( "Could not add packet {} to the send queue", packet );
        }
    }

    /**
     * Notifies the player connection that the player's view distance was changed somehow. This might
     * result in several packets and chunks to be sent in order to account for the change.
     */
    public void onViewDistanceChanged() {
        LOGGER.debug( "View distance changed to {}", this.getEntity().getViewDistance() );
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
        // Update networking first
        this.updateNetwork( currentMillis );

        // Check if we need to send chunks
        if ( this.entity != null ) {
            if ( !this.entity.getChunkSendQueue().isEmpty() ) {
                int maximumInTick = deviceInfo.getOs() == DeviceInfo.DeviceOS.WINDOWS ? 2 : 1;
                int maximumInClientTick = deviceInfo.getOs() == DeviceInfo.DeviceOS.WINDOWS ? 10 : 4;
                int alreadySent = 0;

                int currentX = CoordinateUtils.fromBlockToChunk( (int) this.entity.getPositionX() );
                int currentZ = CoordinateUtils.fromBlockToChunk( (int) this.entity.getPositionZ() );

                // Check if we have a slot
                Queue<ChunkAdapter> queue = this.entity.getChunkSendQueue();
                while ( !queue.isEmpty() && alreadySent < maximumInTick && this.sentInClientTick < maximumInClientTick ) {
                    ChunkAdapter chunk = queue.poll();
                    if ( chunk == null ||
                        Math.abs( chunk.getX() - currentX ) > this.entity.getViewDistance() ||
                        Math.abs( chunk.getZ() - currentZ ) > this.entity.getViewDistance() ||
                        !chunk.getWorld().equals( this.entity.getWorld() ) ) {
                        continue;
                    }

                    this.sendWorldChunk( CoordinateUtils.toLong( chunk.getX(), chunk.getZ() ), chunk.getCachedPacket() );

                    alreadySent++;
                    this.sentInClientTick++;
                }
            }

            while ( !this.entity.getBlockUpdates().isEmpty() ) {
                BlockPosition position = this.entity.getBlockUpdates().poll();
                int chunkX = CoordinateUtils.fromBlockToChunk( position.getX() );
                int chunkZ = CoordinateUtils.fromBlockToChunk( position.getZ() );
                long chunkHash = CoordinateUtils.toLong( chunkX, chunkZ );
                if ( this.playerChunks.contains( chunkHash ) ) {
                    this.entity.getWorld().appendUpdatePackets( this, position );
                }
            }
        }

        if ( this.connection == null ) {
            this.releaseSendQueue();
        } else {
            if ( this.deviceInfo != null && this.deviceInfo.getOs() == DeviceInfo.DeviceOS.WINDOWS ) {
                this.releaseSendQueue();
            }
        }

        // Reset sentInClientTick
        this.lastUpdateDT += dT;
        if ( this.lastUpdateDT >= Values.CLIENT_TICK_RATE ) {
            if ( this.connection != null && ( this.deviceInfo == null || this.deviceInfo.getOs() != DeviceInfo.DeviceOS.WINDOWS ) ) {
                this.releaseSendQueue();
            }

            this.sentInClientTick = 0;
            this.lastUpdateDT = 0;
        }
    }

    private void releaseSendQueue() {
        // Send all queued packets
        if ( this.sendQueue != null && !this.sendQueue.isEmpty() ) {
            if ( this.connection != null ) {
                Packet[] packets = new Packet[this.sendQueue.size()];
                this.sendQueue.toArray( packets );
                this.networkManager.getPostProcessService().execute( new PostProcessWorker( this, packets ) );
                this.sendQueue.clear();
            } else {
                for ( Packet packet : this.sendQueue ) {
                    PacketBuffer buffer = new PacketBuffer( 64 );
                    buffer.writeByte( packet.getId() );
                    buffer.writeShort( (short) 0 );
                    packet.serialize( buffer );

                    WrappedMCPEPacket mcpePacket = new WrappedMCPEPacket();
                    mcpePacket.setBuffer( buffer );
                    this.connectionHandler.send( mcpePacket );
                }

                this.sendQueue.clear();
            }
        }
    }

    private void updateNetwork( long currentMillis ) {
        // Receive all waiting packets:
        if ( this.connection != null ) {
            EncapsulatedPacket packetData;
            while ( ( packetData = this.connection.receive() ) != null ) {
                try {
                    this.handleSocketData( currentMillis, new PacketBuffer( packetData.getPacketData(), 0 ) );
                } catch ( Exception e ) {
                    LOGGER.error( "Error whilst processing packet: ", e );
                }
            }
        } else {
            while ( !this.connectionHandler.getData().isEmpty() ) {
                PacketBuffer buffer = this.connectionHandler.getData().poll();
                try {
                    this.handleSocketData( currentMillis, buffer );
                } catch ( Exception e ) {
                    LOGGER.error( "Error whilst processing packet: ", e );
                }
            }
        }
    }

    /**
     * Sends the given packet to the player.
     *
     * @param packet The packet which should be send to the player
     */
    public void send( Packet packet ) {
        if ( this.connection != null ) {
            if ( !( packet instanceof PacketBatch ) ) {
                this.networkManager.getPostProcessService().execute( new PostProcessWorker( this, new Packet[]{ packet } ) );
            } else {
                PacketBuffer buffer = new PacketBuffer( 64 );
                buffer.writeByte( packet.getId() );
                packet.serialize( buffer );

                this.connection.send( PacketReliability.RELIABLE_ORDERED, packet.orderingChannel(), buffer.getBuffer(), 0, buffer.getPosition() );
            }
        } else {
            PacketBuffer buffer = new PacketBuffer( 64 );
            buffer.writeByte( packet.getId() );
            buffer.writeShort( (short) 0 );
            packet.serialize( buffer );

            WrappedMCPEPacket mcpePacket = new WrappedMCPEPacket();
            mcpePacket.setBuffer( buffer );
            this.connectionHandler.send( mcpePacket );
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
        if ( this.connection != null ) {
            if ( !( packet instanceof PacketBatch ) ) {
                this.networkManager.getPostProcessService().execute( new PostProcessWorker( this, new Packet[]{ packet } ) );
            } else {
                PacketBuffer buffer = new PacketBuffer( 64 );
                buffer.writeByte( packet.getId() );
                packet.serialize( buffer );

                this.connection.send( reliability, orderingChannel, buffer.getBuffer(), 0, buffer.getPosition() );
            }
        } else {
            PacketBuffer buffer = new PacketBuffer( 64 );
            buffer.writeByte( packet.getId() );
            buffer.writeShort( (short) 0 );
            packet.serialize( buffer );

            WrappedMCPEPacket mcpePacket = new WrappedMCPEPacket();
            mcpePacket.setBuffer( buffer );
            this.connectionHandler.send( mcpePacket );
        }
    }

    /**
     * Sends a world chunk to the player. This is used by world adapters in order to give the player connection
     * a chance to know once it is ready for spawning.
     *
     * @param chunkHash The hash of the chunk to keep track of what the player has loaded
     * @param chunkData The chunk data packet to send to the player
     */
    private void sendWorldChunk( long chunkHash, PacketWorldChunk chunkData ) {
        ChunkAdapter chunkAdapter = null;
        if ( ( chunkAdapter = this.entity.getWorld().getChunk( chunkData.getX(), chunkData.getZ() ) ) == null ) {
            return;
        }

        this.playerChunks.add( chunkHash );
        this.addToSendQueue( chunkData );
        this.entity.getEntityVisibilityManager().updateAddedChunk( chunkAdapter );

        if ( this.state == PlayerConnectionState.LOGIN ) {
            this.sentChunks++;
            if ( this.sentChunks >= this.entity.getNeededChunksForSpawn() ) {
                int spawnXChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getX() );
                int spawnZChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getZ() );

                WorldAdapter worldAdapter = this.entity.getWorld();
                worldAdapter.movePlayerToChunk( spawnXChunk, spawnZChunk, this.entity );

                this.getEntity().firstSpawn();

                this.state = PlayerConnectionState.PLAYING;
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
     */
    private void handleSocketData( long currentTimeMillis, PacketBuffer buffer ) {
        if ( buffer.getRemaining() <= 0 ) {
            // Malformed packet:
            return;
        }

        if ( this.connection != null ) {
            while ( buffer.getRemaining() > 0 ) {
                int packetLength = buffer.readUnsignedVarInt();

                byte[] payData = new byte[packetLength];
                buffer.readBytes( payData );
                PacketBuffer pktBuf = new PacketBuffer( payData, 0 );
                this.handleBufferData( currentTimeMillis, pktBuf );

                if ( pktBuf.getRemaining() > 0 ) {
                    LOGGER.error( "Malformed batch packet payload: Could not read enclosed packet data correctly: 0x{} remaining {} bytes", Integer.toHexString( payData[0] ), pktBuf.getRemaining() );
                    return;
                }
            }
        } else {
            this.handleBufferData( currentTimeMillis, buffer );
        }
    }

    private void handleBufferData( long currentTimeMillis, PacketBuffer buffer ) {

        // Grab the packet ID from the packet's data
        byte packetId = buffer.readByte();

        // There is some data behind the packet id when non batched packets (2 bytes)
        if ( packetId == PACKET_BATCH ) {
            LOGGER.error( "Malformed batch packet payload: Batch packets are not allowed to contain further batch packets" );
        }

        // TODO: Proper implement sending subclient and target subclient (two bytes)
        buffer.readShort();

        // If we are still in handshake we only accept certain packets:
        if ( this.state == PlayerConnectionState.HANDSHAKE ) {
            if ( packetId == PACKET_LOGIN ) {
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
            if ( packetId == PACKET_ENCRYPTION_RESPONSE ) {
                this.handlePacket( currentTimeMillis, new PacketEncryptionResponse() );
            } else {
                LOGGER.error( "Received odd packet" );
            }

            // Don't allow for any other packets if we are in RESOURCE_PACK state:
            return;
        }

        // When we are in resource pack state
        if ( this.state == PlayerConnectionState.RESOURCE_PACK ) {
            if ( packetId == PACKET_RESOURCEPACK_RESPONSE ) {
                PacketResourcePackResponse packet = new PacketResourcePackResponse();
                packet.deserialize( buffer );
                this.handlePacket( currentTimeMillis, packet );
            } else {
                LOGGER.error( "Received odd packet" );
            }

            // Don't allow for any other packets if we are in RESOURCE_PACK state:
            return;
        }


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

    /**
     * Handles compressed batch packets directly by decoding their payload.
     *
     * @param buffer The buffer containing the batch packet's data (except packet ID)
     * @return decompressed and decrypted data
     */
    private byte[] handleBatchPacket( PacketBuffer buffer ) {
        // Encrypted?
        byte[] input = new byte[buffer.getRemaining()];
        System.arraycopy( buffer.getBuffer(), buffer.getPosition(), input, 0, input.length );
        if ( this.encryptionHandler != null ) {
            input = this.encryptionHandler.decryptInputFromClient( input );
            if ( input == null ) {
                // Decryption error
                disconnect( "Checksum of encrypted packet was wrong" );
                return null;
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
            return null;
        }

        return bout.toByteArray();
    }

    /**
     * Handles a deserialized packet by dispatching it to the appropriate handler method.
     *
     * @param currentTimeMillis The time this packet arrived at the network manager
     * @param packet            The packet to handle
     */
    @SuppressWarnings( "unchecked" )  // Needed for generic types not matching
    private void handlePacket( long currentTimeMillis, Packet packet ) {
        PacketHandler handler = PACKET_HANDLERS[packet.getId() & 0xff];
        if ( handler != null ) {
            LOGGER.debug( "Packet: {}", packet );
            handler.handle( packet, currentTimeMillis, this );
            return;
        }

        LOGGER.warn( "No handler for {}", packet );
    }

    /**
     * Check if we need to send new chunks to the player
     *
     * @param from which location the entity moved
     */
    public void checkForNewChunks( Location from ) {
        WorldAdapter worldAdapter = this.entity.getWorld();

        int currentXChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getX() );
        int currentZChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getZ() );

        int viewDistance = this.entity.getViewDistance();

        List<Pair<Integer, Integer>> toSendChunks = new ArrayList<>();
        for ( int sendXChunk = currentXChunk - viewDistance; sendXChunk < currentXChunk + viewDistance; sendXChunk++ ) {
            for ( int sendZChunk = currentZChunk - viewDistance; sendZChunk < currentZChunk + viewDistance; sendZChunk++ ) {
                toSendChunks.add( new Pair<>( sendXChunk, sendZChunk ) );
            }
        }

        toSendChunks.sort( ( o1, o2 ) -> {
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
        } );

        for ( Pair<Integer, Integer> chunk : toSendChunks ) {
            long hash = CoordinateUtils.toLong( chunk.getFirst(), chunk.getSecond() );

            if ( !this.playerChunks.contains( hash ) ) {
                worldAdapter.sendChunk( chunk.getFirst(), chunk.getSecond(), this.entity, false );
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
        LongCursor longCursor = this.playerChunks.cursor();
        while ( longCursor.moveNext() ) {
            int x = (int) ( longCursor.elem() >> 32 );
            int z = (int) ( longCursor.elem() ) + Integer.MIN_VALUE;

            if ( x > currentXChunk + viewDistance ||
                x < currentXChunk - viewDistance ||
                z > currentZChunk + viewDistance ||
                z < currentZChunk - viewDistance ) {
                // TODO: Check for Packets to send to the client to unload the chunk?
                this.entity.getEntityVisibilityManager().updateRemoveChunk( this.entity.getWorld().getChunk( x, z ) );
                longCursor.remove();
            }
        }
    }

    /**
     * Send resource packs
     */
    public void initWorldAndResourceSend() {
        // We have the chance of forcing resource and behaviour packs here
        PacketResourcePacksInfo packetResourcePacksInfo = new PacketResourcePacksInfo();
        this.send( packetResourcePacksInfo );
    }

    /**
     * Send chunk radius
     */
    private void sendChunkRadiusUpdate() {
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
        if ( this.connection != null ) {
            if ( this.connection.isConnected() && !this.connection.isDisconnecting() ) {
                this.networkManager.getServer().getPluginManager().callEvent( new PlayerKickEvent( this.entity, message ) );

                if ( message != null && message.length() > 0 ) {
                    PacketDisconnect packet = new PacketDisconnect();
                    packet.setMessage( message );
                    this.send( packet );
                }

                if ( this.entity != null ) {
                    LOGGER.info( "EntityPlayer " + this.entity.getName() + " left the game: " + message );
                } else {
                    LOGGER.info( "EntityPlayer has been disconnected whilst logging in: " + message );
                }

                this.connection.disconnect( message );
            }
        } else {
            this.networkManager.getServer().getPluginManager().callEvent( new PlayerKickEvent( this.entity, message ) );

            if ( message != null && message.length() > 0 ) {
                PacketDisconnect packet = new PacketDisconnect();
                packet.setMessage( message );
                this.send( packet );
            }

            if ( this.entity != null ) {
                LOGGER.info( "EntityPlayer " + this.entity.getName() + " left the game: " + message );
            } else {
                LOGGER.info( "EntityPlayer has been disconnected whilst logging in: " + message );
            }

            this.connectionHandler.disconnect();
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
        move.setHeadYaw( location.getYaw() );
        move.setYaw( location.getYaw() );
        move.setPitch( location.getPitch() );
        move.setMode( (byte) 2 );
        move.setOnGround( this.getEntity().isOnGround() );
        move.setRidingEntityId( 0 );    // TODO: Implement riding entities correctly
        this.addToSendQueue( move );
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
        this.addToSendQueue( time );
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
        packet.setDifficulty( this.entity.getWorld().getDifficulty().getDifficultyDegree() );
        packet.setLevelId( Base64.getEncoder().encodeToString( StringUtil.getUTF8Bytes( world.getWorldName() ) ) );
        packet.setWorldName( world.getWorldName() );
        packet.setTemplateName( "" );
        packet.setGamerules( world.getGamerules() );
        packet.setTexturePacksRequired( false );
        packet.setCommandsEnabled( true );

        this.entity.setPosition( world.getSpawnLocation() );
        this.addToSendQueue( packet );
    }

    /**
     * The underlying RakNet Connection closed. Cleanup
     */
    void close() {
        if ( this.entity != null && this.entity.getWorld() != null ) {
            this.networkManager.getServer().getPluginManager().callEvent( new PlayerQuitEvent( this.entity ) );
            this.entity.getWorld().removePlayer( this.entity );
            this.entity.cleanup();
            this.entity.setDead( true );
            this.networkManager.getServer().getPluginManager().callEvent( new PlayerCleanedupEvent( this.entity ) );
            this.entity = null;
        }
    }

    /**
     * Clear the chunks which we know the player has gotten
     */
    public void resetPlayerChunks() {
        this.playerChunks.clear();
        this.entity.getChunkSendQueue().clear();
    }

    /**
     * Get this connection ping
     *
     * @return ping of UDP connection or 0 when TCP is used
     */
    public int getPing() {
        return ( this.connection != null ) ? (int) this.connection.getPing() : this.tcpPing;
    }

    public long getId() {
        return ( this.connection != null ) ? this.connection.getGuid() : this.tcpId;
    }

    @Override
    public String toString() {
        return this.entity != null ? this.entity.getName() : ( this.connection != null ) ? String.valueOf( this.connection.getGuid() ) : "unknown";
    }

    public void sendSpawnPosition() {
        PacketSetSpawnPosition spawnPosition = new PacketSetSpawnPosition();
        spawnPosition.setSpawnType( 1 );
        spawnPosition.setForce( false );
        spawnPosition.setPosition( this.getEntity().getWorld().getSpawnLocation().toBlockPosition() );
        addToSendQueue( spawnPosition );
    }

    public void sendDifficulty() {
        PacketSetDifficulty setDifficulty = new PacketSetDifficulty();
        setDifficulty.setDifficulty( this.entity.getWorld().getDifficulty().getDifficultyDegree() );
        addToSendQueue( setDifficulty );
    }

    public void sendCommandsEnabled() {
        PacketSetCommandsEnabled setCommandsEnabled = new PacketSetCommandsEnabled();
        setCommandsEnabled.setEnabled( true );
        addToSendQueue( setCommandsEnabled );
    }

}
