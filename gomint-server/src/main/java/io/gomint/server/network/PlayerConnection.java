/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.event.player.PlayerCleanedupEvent;
import io.gomint.event.player.PlayerKickEvent;
import io.gomint.event.player.PlayerQuitEvent;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.PacketReliability;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.player.DeviceInfo;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.passive.EntityHuman;
import io.gomint.server.jni.NativeCode;
import io.gomint.server.jni.zlib.JavaZLib;
import io.gomint.server.jni.zlib.NativeZLib;
import io.gomint.server.jni.zlib.ZLib;
import io.gomint.server.network.handler.PacketAdventureSettingsHandler;
import io.gomint.server.network.handler.PacketAnimateHandler;
import io.gomint.server.network.handler.PacketBlockPickRequestHandler;
import io.gomint.server.network.handler.PacketBookEditHandler;
import io.gomint.server.network.handler.PacketCommandRequestHandler;
import io.gomint.server.network.handler.PacketContainerCloseHandler;
import io.gomint.server.network.handler.PacketCraftingEventHandler;
import io.gomint.server.network.handler.PacketEncryptionResponseHandler;
import io.gomint.server.network.handler.PacketEntityEventHandler;
import io.gomint.server.network.handler.PacketEntityFallHandler;
import io.gomint.server.network.handler.PacketHandler;
import io.gomint.server.network.handler.PacketHotbarHandler;
import io.gomint.server.network.handler.PacketInteractHandler;
import io.gomint.server.network.handler.PacketInventoryTransactionHandler;
import io.gomint.server.network.handler.PacketLoginHandler;
import io.gomint.server.network.handler.PacketMobArmorEquipmentHandler;
import io.gomint.server.network.handler.PacketMobEquipmentHandler;
import io.gomint.server.network.handler.PacketModalResponseHandler;
import io.gomint.server.network.handler.PacketMovePlayerHandler;
import io.gomint.server.network.handler.PacketPlayerActionHandler;
import io.gomint.server.network.handler.PacketResourcePackResponseHandler;
import io.gomint.server.network.handler.PacketServerSettingsRequestHandler;
import io.gomint.server.network.handler.PacketSetChunkRadiusHandler;
import io.gomint.server.network.handler.PacketSetLocalPlayerAsInitializedHandler;
import io.gomint.server.network.handler.PacketTextHandler;
import io.gomint.server.network.handler.PacketWorldSoundEventHandler;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import io.gomint.server.network.packet.PacketConfirmChunkRadius;
import io.gomint.server.network.packet.PacketDisconnect;
import io.gomint.server.network.packet.PacketEncryptionResponse;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.server.network.packet.PacketLogin;
import io.gomint.server.network.packet.PacketMovePlayer;
import io.gomint.server.network.packet.PacketPlayState;
import io.gomint.server.network.packet.PacketPlayerlist;
import io.gomint.server.network.packet.PacketResourcePackResponse;
import io.gomint.server.network.packet.PacketResourcePacksInfo;
import io.gomint.server.network.packet.PacketSetCommandsEnabled;
import io.gomint.server.network.packet.PacketSetDifficulty;
import io.gomint.server.network.packet.PacketSetSpawnPosition;
import io.gomint.server.network.packet.PacketStartGame;
import io.gomint.server.network.packet.PacketWorldTime;
import io.gomint.server.network.tcp.ConnectionHandler;
import io.gomint.server.network.tcp.protocol.FlushTickPacket;
import io.gomint.server.network.tcp.protocol.WrappedMCPEPacket;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.Pair;
import io.gomint.server.util.StringUtil;
import io.gomint.server.util.Values;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.WorldAdapter;
import io.gomint.util.random.FastRandom;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.DataFormatException;

import static io.gomint.server.network.Protocol.PACKET_BATCH;
import static io.gomint.server.network.Protocol.PACKET_ENCRYPTION_RESPONSE;
import static io.gomint.server.network.Protocol.PACKET_LOGIN;
import static io.gomint.server.network.Protocol.PACKET_RESOURCEPACK_RESPONSE;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PlayerConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger( PlayerConnection.class );
    private static final NativeCode<ZLib> ZLIB = new NativeCode<>( "zlib", JavaZLib.class, NativeZLib.class );
    private static final PacketHandler[] PACKET_HANDLERS = new PacketHandler[256];

    static {
        // Load zlib native
        ZLIB.load();

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
        PACKET_HANDLERS[Protocol.PACKET_BLOCK_PICK_REQUEST & 0xff] = new PacketBlockPickRequestHandler();
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
        PACKET_HANDLERS[Protocol.PACKET_ENTITY_FALL & 0xFF] = new PacketEntityFallHandler();
        PACKET_HANDLERS[Protocol.PACKET_BOOK_EDIT & 0xFF] = new PacketBookEditHandler();
        PACKET_HANDLERS[Protocol.PACKET_SET_LOCAL_PLAYER_INITIALIZED & 0xff] = new PacketSetLocalPlayerAsInitializedHandler();
    }

    // Network manager that created this connection:
    @Getter
    private final NetworkManager networkManager;

    // Actual connection for wire transfer:
    @Getter
    private final Connection connection;
    @Getter
    private final ConnectionHandler connectionHandler;

    // World data
    @Getter
    private final LongSet playerChunks;
    @Getter
    private final LongSet loadingChunks;
    @Getter
    @Setter
    private EncryptionHandler encryptionHandler;
    @Getter
    private GoMintServer server;
    @Setter
    @Getter
    private int protocolID;
    @Setter
    private long tcpId;
    @Setter
    private int tcpPing;
    private PostProcessExecutor postProcessorExecutor;
    private ZLib decompressor;

    // Connection State:
    @Getter
    @Setter
    private PlayerConnectionState state;
    private List<Packet> sendQueue;

    // Entity
    @Getter
    @Setter
    private EntityPlayer entity;

    // Additional data
    @Getter
    @Setter
    private DeviceInfo deviceInfo;
    private float lastUpdateDT = 0;

    // Anti spam because mojang likes to send data
    @Setter
    @Getter
    private boolean hadStartBreak;
    @Setter
    @Getter
    private boolean startBreakResult;
    @Getter
    private Set<PacketInventoryTransaction> transactionsHandled = new HashSet<>();

    // Debug stuff
    @Getter
    private AtomicInteger responseChunks = new AtomicInteger( 0 );

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
        this.playerChunks = new LongOpenHashSet();
        this.loadingChunks = new LongOpenHashSet();

        // Attach data processor if needed
        if ( this.connection != null ) {
            this.decompressor = ZLIB.newInstance();
            this.decompressor.init( false, false, 7 ); // Level doesn't matter

            this.postProcessorExecutor = networkManager.getPostProcessService().getExecutor();
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
        if ( !GoMint.instance().isMainThread() ) {
            LOGGER.warn( "Add packet async to send queue - canceling sending", new Exception() );
            return;
        }

        if ( this.sendQueue == null ) {
            this.sendQueue = new ArrayList<>();
        }

        if ( !this.sendQueue.add( packet ) ) {
            LOGGER.warn( "Could not add packet {} to the send queue", packet );
        }
    }

    /**
     * Notifies the player connection that the player's view distance was changed somehow. This might
     * result in several packets and chunks to be sent in order to account for the change.
     */
    public void onViewDistanceChanged() {
        LOGGER.info( "View distance changed to {}", this.getEntity().getViewDistance() );
        this.checkForNewChunks( null, false );
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

        // Clear spam stuff
        this.startBreakResult = false;
        this.hadStartBreak = false;
        this.transactionsHandled.clear();

        // Reset sentInClientTick
        this.lastUpdateDT += dT;
        if ( this.lastUpdateDT >= Values.CLIENT_TICK_RATE ) {
            // Check if we need to send chunks
            if ( this.entity != null && !this.entity.getChunkSendQueue().isEmpty() ) {
                int currentX = CoordinateUtils.fromBlockToChunk( (int) this.entity.getPositionX() );
                int currentZ = CoordinateUtils.fromBlockToChunk( (int) this.entity.getPositionZ() );

                // Check if we have a slot
                Queue<ChunkAdapter> queue = this.entity.getChunkSendQueue();
                List<ChunkAdapter> recheck = null;

                while ( !queue.isEmpty() ) {
                    ChunkAdapter chunk = queue.poll();
                    if ( chunk == null ||
                        Math.abs( chunk.getX() - currentX ) > this.entity.getViewDistance() ||
                        Math.abs( chunk.getZ() - currentZ ) > this.entity.getViewDistance() ||
                        !chunk.getWorld().equals( this.entity.getWorld() ) ) {
                        LOGGER.debug( "Removed chunk from sending due to out of scope" );
                        continue;
                    }

                    // Check if chunk has been populated
                    if ( !chunk.isPopulated() ) {
                        if ( recheck == null ) {
                            recheck = new ArrayList<>();
                        }

                        recheck.add( chunk );
                        continue;
                    }

                    this.sendWorldChunk( chunk );
                }

                if ( recheck != null ) {
                    for ( ChunkAdapter adapter : recheck ) {
                        queue.offer( adapter );
                    }
                }
            }

            if ( this.entity != null && !this.entity.getBlockUpdates().isEmpty() ) {
                for ( BlockPosition position : this.entity.getBlockUpdates() ) {
                    int chunkX = CoordinateUtils.fromBlockToChunk( position.getX() );
                    int chunkZ = CoordinateUtils.fromBlockToChunk( position.getZ() );
                    long chunkHash = CoordinateUtils.toLong( chunkX, chunkZ );
                    if ( this.playerChunks.contains( chunkHash ) ) {
                        this.entity.getWorld().appendUpdatePackets( this, position );
                    }
                }

                this.entity.getBlockUpdates().clear();
            }

            this.releaseSendQueue();
            this.lastUpdateDT = 0;
        }
    }

    private void releaseSendQueue() {
        // Send all queued packets
        if ( this.connection != null ) {
            if ( this.sendQueue != null && !this.sendQueue.isEmpty() ) {
                this.postProcessorExecutor.addWork( this, this.sendQueue.toArray( new Packet[0] ) );
                this.sendQueue.clear();
            }
        } else {
            if ( this.sendQueue != null && !this.sendQueue.isEmpty() ) {
                Packet[] packets = this.sendQueue.toArray( new Packet[0] );
                PacketBuffer[] packetBuffers = new PacketBuffer[packets.length];
                for ( int i = 0; i < packets.length; i++ ) {
                    packetBuffers[i] = new PacketBuffer( 2 );
                    packets[i].serializeHeader( packetBuffers[i] );
                    packets[i].serialize( packetBuffers[i], this.protocolID );
                }

                WrappedMCPEPacket mcpePacket = new WrappedMCPEPacket();
                mcpePacket.setRaknetVersion( (byte) 9 );
                mcpePacket.setBuffer( packetBuffers );
                this.connectionHandler.send( mcpePacket );
                this.sendQueue.clear();
            }

            this.connectionHandler.send( new FlushTickPacket() );
        }
    }

    private void updateNetwork( long currentMillis ) {
        // It seems that movement is sent last, but we need it first to check if player position of other packets align
        List<PacketBuffer> packetBuffers = null;

        // Receive all waiting packets:
        if ( this.connection != null ) {
            EncapsulatedPacket packetData;
            while ( ( packetData = this.connection.receive() ) != null ) {
                if ( packetBuffers == null ) {
                    packetBuffers = new ArrayList<>();
                }

                packetBuffers.add( new PacketBuffer( packetData.getPacketData(), 0 ) );
            }
        } else {
            while ( !this.connectionHandler.getData().isEmpty() ) {
                PacketBuffer buffer = this.connectionHandler.getData().poll();

                if ( packetBuffers == null ) {
                    packetBuffers = new ArrayList<>();
                }

                packetBuffers.add( buffer );
            }
        }

        if ( packetBuffers != null ) {
            for ( PacketBuffer buffer : packetBuffers ) {
                // CHECKSTYLE:OFF
                try {
                    this.handleSocketData( currentMillis, buffer );
                } catch ( Exception e ) {
                    LOGGER.error( "Error whilst processing packet: ", e );
                }
                // CHECKSTYLE:ON
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
                this.postProcessorExecutor.addWork( this, new Packet[]{ packet } );
            } else {
                PacketBuffer buffer = new PacketBuffer( 64 );
                buffer.writeByte( packet.getId() );
                packet.serialize( buffer, this.protocolID );

                this.connection.send( PacketReliability.RELIABLE_ORDERED, packet.orderingChannel(), buffer.getBuffer(), 0, buffer.getPosition() );
            }
        } else {
            LOGGER.debug( "Writing packet {} to client", Integer.toHexString( packet.getId() & 0xFF ) );

            PacketBuffer buffer = new PacketBuffer( 2 );
            packet.serializeHeader( buffer );
            packet.serialize( buffer, this.protocolID );

            WrappedMCPEPacket mcpePacket = new WrappedMCPEPacket();
            mcpePacket.setRaknetVersion( (byte) 9 );
            mcpePacket.setBuffer( new PacketBuffer[]{ buffer } );
            this.connectionHandler.send( mcpePacket );
        }
    }

    /**
     * Sends a world chunk to the player. This is used by world adapters in order to give the player connection
     * a chance to know once it is ready for spawning.
     *
     * @param chunkAdapter which should be sent to the client
     * @return true when the chunk has been sent, false when not
     */
    private boolean sendWorldChunk( ChunkAdapter chunkAdapter ) {
        this.playerChunks.add( chunkAdapter.longHashCode() );
        this.loadingChunks.remove( chunkAdapter.longHashCode() );
        this.addToSendQueue( chunkAdapter.getCachedPacket() );
        this.entity.getEntityVisibilityManager().updateAddedChunk( chunkAdapter );

        if ( this.state == PlayerConnectionState.LOGIN && this.loadingChunks.isEmpty() ) {
            int spawnXChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getX() );
            int spawnZChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getZ() );

            WorldAdapter worldAdapter = this.entity.getWorld();
            worldAdapter.movePlayerToChunk( spawnXChunk, spawnZChunk, this.entity );

            this.getEntity().firstSpawn();

            this.state = PlayerConnectionState.PLAYING;

            this.entity.getLoginPerformance().setChunkEnd( this.entity.getWorld().getServer().getCurrentTickTime() );
            this.entity.getLoginPerformance().print();
        }

        return true;
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
        int rawId = buffer.readUnsignedVarInt();
        byte packetId = (byte) rawId;

        // There is some data behind the packet id when non batched packets (2 bytes)
        if ( packetId == PACKET_BATCH ) {
            LOGGER.error( "Malformed batch packet payload: Batch packets are not allowed to contain further batch packets" );
        }

        LOGGER.debug( "Got MCPE packet {}", Integer.toHexString( packetId & 0xFF ) );

        // If we are still in handshake we only accept certain packets:
        if ( this.state == PlayerConnectionState.HANDSHAKE ) {
            if ( packetId == PACKET_LOGIN ) {
                PacketLogin packet = new PacketLogin();
                packet.deserialize( buffer, this.protocolID );
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
                packet.deserialize( buffer, this.protocolID );
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

        packet.deserialize( buffer, this.protocolID );
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

        ByteBuf inBuf = PooledByteBufAllocator.DEFAULT.directBuffer( input.length );
        inBuf.writeBytes( input );

        ByteBuf outBuf = PooledByteBufAllocator.DEFAULT.directBuffer( 8192 ); // We will write at least once so ensureWrite will realloc to 8192 so or so

        try {
            this.decompressor.process( inBuf, outBuf );
        } catch ( DataFormatException e ) {
            LOGGER.error( "Failed to decompress batch packet", e );
            outBuf.release();
            return null;
        } finally {
            inBuf.release();
        }

        byte[] data = new byte[outBuf.readableBytes()];
        outBuf.readBytes( data );
        outBuf.release();
        return data;
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
     * @param from                which location the entity moved
     * @param forceResendEntities should we resend all entities known?
     */
    public void checkForNewChunks( Location from, boolean forceResendEntities ) {
        WorldAdapter worldAdapter = this.entity.getWorld();

        int currentXChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getX() );
        int currentZChunk = CoordinateUtils.fromBlockToChunk( (int) this.entity.getLocation().getZ() );

        int viewDistance = this.entity.getViewDistance();

        List<Pair<Integer, Integer>> toSendChunks = new ArrayList<>();

        for ( int sendXChunk = currentXChunk - viewDistance; sendXChunk <= currentXChunk + viewDistance; sendXChunk++ ) {
            for ( int sendZChunk = currentZChunk - viewDistance; sendZChunk <= currentZChunk + viewDistance; sendZChunk++ ) {
                if ( forceResendEntities ) {
                    toSendChunks.add( new Pair<>( sendXChunk, sendZChunk ) );
                } else {
                    long hash = CoordinateUtils.toLong( sendXChunk, sendZChunk );
                    if ( !this.playerChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                        toSendChunks.add( new Pair<>( sendXChunk, sendZChunk ) );
                    }
                }
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

        if ( forceResendEntities ) {
            this.entity.getEntityVisibilityManager().clear();
        }

        for ( Pair<Integer, Integer> chunk : toSendChunks ) {
            if ( forceResendEntities ) {
                long hash = CoordinateUtils.toLong( chunk.getFirst(), chunk.getSecond() );
                if ( !this.playerChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                    this.requestChunk( chunk.getFirst(), chunk.getSecond() );
                } else {
                    // We already know this chunk but maybe forceResend is enabled
                    worldAdapter.sendChunk( chunk.getFirst(), chunk.getSecond(),
                        false, ( chunkHash, loadedChunk ) -> {
                            if ( this.entity != null ) { // It can happen that the server loads longer and the client has disconnected
                                this.entity.getEntityVisibilityManager().updateAddedChunk( loadedChunk );
                            }
                        } );
                }
            } else {
                this.requestChunk( chunk.getFirst(), chunk.getSecond() );
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
        LongIterator longCursor = this.playerChunks.iterator();
        while ( longCursor.hasNext() ) {
            long hash = longCursor.nextLong();
            int x = (int) ( hash >> 32 );
            int z = (int) ( hash ) + Integer.MIN_VALUE;

            if ( Math.abs( x - currentXChunk ) > viewDistance ||
                Math.abs( z - currentZChunk ) > viewDistance ) {
                ChunkAdapter chunk = this.entity.getWorld().getChunk( x, z );
                if ( chunk == null ) {
                    LOGGER.error( "Wanted to update state on already unloaded chunk {} {}", x, z );
                } else {
                    // TODO: Check for Packets to send to the client to unload the chunk?
                    this.entity.getEntityVisibilityManager().updateRemoveChunk( chunk );
                }

                longCursor.remove();
            }
        }
    }

    private void requestChunk( Integer x, Integer z ) {
        LOGGER.debug( "Requesting chunk {} {} for {}", x, z, this.entity );
        this.entity.getWorld().sendChunk( x, z,
            false, ( chunkHash, loadedChunk ) -> {
                if ( this.entity != null ) { // It can happen that the server loads longer and the client has disconnected
                    this.entity.getChunkSendQueue().offer( loadedChunk );
                }
            } );
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
        this.networkManager.getServer().getPluginManager().callEvent( new PlayerKickEvent( this.entity, message ) );

        if ( message != null && message.length() > 0 ) {
            PacketDisconnect packet = new PacketDisconnect();
            packet.setMessage( message );
            this.send( packet );

            this.server.getExecutorService().schedule( () -> PlayerConnection.this.internalClose( message ), 3, TimeUnit.SECONDS );
        } else {
            this.internalClose( message );
        }

        if ( this.entity != null ) {
            LOGGER.info( "EntityPlayer {} left the game: {}", this.entity.getName(), message );
        } else {
            LOGGER.info( "EntityPlayer has been disconnected whilst logging in: {}", message );
        }
    }

    private void internalClose( String message ) {
        if ( this.connection != null ) {
            if ( this.connection.isConnected() && !this.connection.isDisconnecting() ) {
                this.connection.disconnect( message );
            }
        } else {
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
        move.setHeadYaw( location.getHeadYaw() );
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

        if ( this.entity.getSpawnLocation() != null ) {
            packet.setSpawn( this.entity.getSpawnLocation().add( 0, this.entity.getOffsetY(), 0 ) );
            packet.setX( (int) this.entity.getSpawnLocation().getX() );
            packet.setY( (int) ( this.entity.getSpawnLocation().getY() + this.entity.getOffsetY() ) );
            packet.setZ( (int) this.entity.getSpawnLocation().getZ() );
        } else {
            packet.setSpawn( world.getSpawnLocation().add( 0, this.entity.getOffsetY(), 0 ) );
            packet.setX( (int) world.getSpawnLocation().getX() );
            packet.setY( (int) ( world.getSpawnLocation().getY() + this.entity.getOffsetY() ) );
            packet.setZ( (int) world.getSpawnLocation().getZ() );
        }

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
        packet.setEnchantmentSeed( FastRandom.current().nextInt() );
        packet.setCorrelationId( this.server.getServerUniqueID().toString() );

        // Set the new location
        this.entity.setAndRecalcPosition( this.entity.getSpawnLocation() != null ? this.entity.getSpawnLocation() : world.getSpawnLocation() );
        this.addToSendQueue( packet );
    }

    /**
     * The underlying RakNet Connection closed. Cleanup
     */
    void close() {
        LOGGER.info( "Player {} disconnected", this.entity );

        if ( this.entity != null && this.entity.getWorld() != null ) {
            PlayerQuitEvent event = this.networkManager.getServer().getPluginManager().callEvent( new PlayerQuitEvent( this.entity, ChatColor.YELLOW + this.entity.getDisplayName() + " left the game." ) );
            if( event.getQuitMessage() != null && !event.getQuitMessage().isEmpty() ) {
                this.getServer().getPlayers().forEach( ( player ) -> {
                    player.sendMessage( event.getQuitMessage() );
                } );
            }
            this.entity.getWorld().removePlayer( this.entity );
            this.entity.cleanup();
            this.entity.setDead( true );
            this.networkManager.getServer().getPluginManager().callEvent( new PlayerCleanedupEvent( this.entity ) );
            this.entity = null;
        }

        if ( this.postProcessorExecutor != null ) {
            this.networkManager.getPostProcessService().releaseExecutor( this.postProcessorExecutor );
        }
    }

    /**
     * Clear the chunks which we know the player has gotten
     */
    public void resetPlayerChunks() {
        this.loadingChunks.clear();
        this.playerChunks.clear();
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

    public void sendPlayerSpawnPosition() {
        PacketSetSpawnPosition spawnPosition = new PacketSetSpawnPosition();
        spawnPosition.setSpawnType( PacketSetSpawnPosition.SpawnType.PLAYER );
        spawnPosition.setForce( false );
        spawnPosition.setPosition( this.getEntity().getSpawnLocation().toBlockPosition() );
        addToSendQueue( spawnPosition );
    }

    public void sendSpawnPosition() {
        PacketSetSpawnPosition spawnPosition = new PacketSetSpawnPosition();
        spawnPosition.setSpawnType( PacketSetSpawnPosition.SpawnType.WORLD );
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

    public void resetQueuedChunks() {
        if ( !this.entity.getChunkSendQueue().isEmpty() ) {
            for ( ChunkAdapter adapter : this.entity.getChunkSendQueue() ) {
                long hash = CoordinateUtils.toLong( adapter.getX(), adapter.getZ() );
                this.loadingChunks.remove( hash );
            }
        }

        this.entity.getChunkSendQueue().clear();
    }

    public void spawnPlayerEntities() {
        // Now its ok to send players
        this.entity.setSpawnPlayers( true );

        // Send player list for all online players
        List<PacketPlayerlist.Entry> listEntry = null;
        for ( io.gomint.entity.EntityPlayer player : this.getServer().getPlayers() ) {
            if ( !this.entity.isHidden( player ) && !this.entity.equals( player ) ) {
                if ( listEntry == null ) {
                    listEntry = new ArrayList<>();
                }

                listEntry.add( new PacketPlayerlist.Entry( (EntityHuman) player ) );
            }
        }

        if ( listEntry != null ) {
            // Send player list
            PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
            packetPlayerlist.setMode( (byte) 0 );
            packetPlayerlist.setEntries( listEntry );
            this.send( packetPlayerlist );
        }

        // Show all players
        LongIterator playerChunksIterator = this.playerChunks.iterator();
        while ( playerChunksIterator.hasNext() ) {
            long chunkHash = playerChunksIterator.nextLong();

            int currentX = (int) ( chunkHash >> 32 );
            int currentZ = (int) ( chunkHash ) + Integer.MIN_VALUE;

            ChunkAdapter chunk = this.entity.getWorld().getChunk( currentX, currentZ );
            this.entity.getEntityVisibilityManager().updateAddedChunk( chunk );
        }
    }

}
