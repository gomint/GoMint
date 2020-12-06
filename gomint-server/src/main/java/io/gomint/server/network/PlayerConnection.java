/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.crypto.Processor;
import io.gomint.event.player.PlayerCleanedupEvent;
import io.gomint.event.player.PlayerKickEvent;
import io.gomint.event.player.PlayerQuitEvent;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.PacketReliability;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.player.DeviceInfo;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.network.handler.*;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import io.gomint.server.network.packet.PacketConfirmChunkRadius;
import io.gomint.server.network.packet.PacketDisconnect;
import io.gomint.server.network.packet.PacketEncryptionResponse;
import io.gomint.server.network.packet.PacketHotbar;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.server.network.packet.PacketLogin;
import io.gomint.server.network.packet.PacketMovePlayer;
import io.gomint.server.network.packet.PacketMovePlayer.MovePlayerMode;
import io.gomint.server.network.packet.PacketNetworkChunkPublisherUpdate;
import io.gomint.server.network.packet.PacketPlayState;
import io.gomint.server.network.packet.PacketPlayerlist;
import io.gomint.server.network.packet.PacketResourcePackResponse;
import io.gomint.server.network.packet.PacketResourcePacksInfo;
import io.gomint.server.network.packet.PacketSetCommandsEnabled;
import io.gomint.server.network.packet.PacketSetDifficulty;
import io.gomint.server.network.packet.PacketSetSpawnPosition;
import io.gomint.server.network.packet.PacketSkipable;
import io.gomint.server.network.packet.PacketStartGame;
import io.gomint.server.network.packet.PacketWorldTime;
import io.gomint.server.util.Cache;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.Pair;
import io.gomint.server.util.StringUtil;
import io.gomint.server.util.Values;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Biome;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.gomint.server.network.Protocol.BATCH_MAGIC;
import static io.gomint.server.network.Protocol.PACKET_ENCRYPTION_RESPONSE;
import static io.gomint.server.network.Protocol.PACKET_LOGIN;
import static io.gomint.server.network.Protocol.PACKET_RESOURCEPACK_RESPONSE;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PlayerConnection implements ConnectionWithState {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerConnection.class);

    // Network manager that created this connection:
    private final NetworkManager networkManager;

    // Actual connection for wire transfer:
    private final Connection connection;

    // Caching state
    private boolean cachingSupported;
    private final Cache cache = new Cache();

    // World data
    private final LongSet playerChunks;
    private final LongSet loadingChunks;
    private final GoMintServer server;
    private int protocolID;
    private PostProcessExecutor postProcessorExecutor;

    // Connection State:
    private PlayerConnectionState state;
    private List<Packet> sendQueue;

    // Entity
    private EntityPlayer entity;

    // Additional data
    private DeviceInfo deviceInfo;
    private float lastUpdateDT = 0;

    // Anti spam because mojang likes to send data
    private boolean hadStartBreak;
    private boolean startBreakResult;
    private Set<PacketInventoryTransaction> transactionsHandled = new HashSet<>();

    // Processors
    private Processor inputProcessor = new Processor(false);
    private Processor outputProcessor = new Processor(true);

    /**
     * Constructs a new player connection.
     *
     * @param networkManager The network manager creating this instance
     * @param connection     The jRakNet connection for actual wire-transfer
     */
    PlayerConnection(NetworkManager networkManager, Connection connection) {
        this.networkManager = networkManager;
        this.connection = connection;
        this.state = PlayerConnectionState.HANDSHAKE;
        this.server = networkManager.getServer();
        this.playerChunks = new LongOpenHashSet();
        this.loadingChunks = new LongOpenHashSet();

        // Attach data processor if needed
        if (this.connection != null) {
            this.postProcessorExecutor = networkManager.getPostProcessService().getExecutor();
            this.connection.addDataProcessor(packetData -> {
                if (packetData.getPacketData().readableBytes() <= 0) {
                    // Malformed packet:
                    return packetData;
                }

                // Check if packet is batched
                byte packetId = packetData.getPacketData().readByte();
                if (packetId == Protocol.BATCH_MAGIC) {
                    // Decompress and decrypt
                    ByteBuf pureData = handleBatchPacket(packetData.getPacketData());
                    EncapsulatedPacket newPacket = new EncapsulatedPacket();
                    newPacket.setPacketData(pureData);

                    pureData.release(); // The packet takes over ownership
                    // We don't need to release the input because the batch packet handler releases it
                    return newPacket;
                }

                packetData.getPacketData().readerIndex(0);
                return packetData;
            });
        }
    }

    public Processor getInputProcessor() {
        return inputProcessor;
    }

    @Override
    public Processor getOutputProcessor() {
        return outputProcessor;
    }

    public Set<PacketInventoryTransaction> getTransactionsHandled() {
        return transactionsHandled;
    }

    public void setStartBreakResult(boolean startBreakResult) {
        this.startBreakResult = startBreakResult;
    }

    public boolean isStartBreakResult() {
        return startBreakResult;
    }

    public void setHadStartBreak(boolean hadStartBreak) {
        this.hadStartBreak = hadStartBreak;
    }

    public boolean isHadStartBreak() {
        return hadStartBreak;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setEntity(EntityPlayer entity) {
        this.entity = entity;
    }

    public EntityPlayer getEntity() {
        return entity;
    }

    public void setState(PlayerConnectionState state) {
        this.state = state;
    }

    @Override
    public PlayerConnectionState getState() {
        return state;
    }

    public void setProtocolID(int protocolID) {
        this.protocolID = protocolID;
    }

    @Override
    public int getProtocolID() {
        return protocolID;
    }

    public GoMintServer getServer() {
        return server;
    }

    public LongSet getLoadingChunks() {
        return loadingChunks;
    }

    public LongSet getPlayerChunks() {
        return playerChunks;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCachingSupported(boolean cachingSupported) {
        this.cachingSupported = cachingSupported;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Add a packet to the queue to be batched in the next tick
     *
     * @param packet The packet which should be queued
     */
    public void addToSendQueue(Packet packet) {
        if (!GoMint.instance().isMainThread()) {
            LOGGER.warn("Add packet async to send queue - canceling sending", new Exception());
            return;
        }

        if (!this.connection.isConnected()) {
            return;
        }

        if (this.sendQueue == null) {
            this.sendQueue = new ArrayList<>();
        }

        this.sendQueue.add(packet);
        LOGGER.debug("Added packet {} to be sent to {}", packet, this.entity != null ? this.entity.getName() : "UNKNOWN");
    }

    /**
     * Notifies the player connection that the player's view distance was changed somehow. This might
     * result in several packets and chunks to be sent in order to account for the change.
     */
    public void onViewDistanceChanged() {
        LOGGER.info("View distance changed to {}", this.getEntity().getViewDistance());
        this.checkForNewChunks(null, false);
        this.sendChunkRadiusUpdate();
    }

    /**
     * Performs a network tick on this player connection. All incoming packets are received and handled
     * accordingly.
     *
     * @param currentMillis Time when the tick started
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    public void update(long currentMillis, float dT) {
        // Update networking first
        this.updateNetwork(currentMillis);

        // Clear spam stuff
        this.startBreakResult = false;
        this.hadStartBreak = false;
        this.transactionsHandled.clear();

        // Reset sentInClientTick
        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            if (this.entity != null) {
                // Check if we need to send chunks
                if (!this.entity.getChunkSendQueue().isEmpty()) {
                    // Check if we have a slot
                    Queue<ChunkAdapter> queue = this.entity.getChunkSendQueue();
                    int sent = 0;


                    int maxSent = this.server.getServerConfig().getSendChunksPerTick();
                    if (this.server.getServerConfig().isEnableFastJoin() && this.state == PlayerConnectionState.LOGIN) {
                        maxSent = Integer.MAX_VALUE;
                    }

                    while (!queue.isEmpty() && sent <= maxSent) {
                        ChunkAdapter chunk = queue.peek();
                        if (chunk == null) {
                            break;
                        }

                        if (!this.loadingChunks.contains(chunk.longHashCode())) {
                            LOGGER.debug("Removed chunk from sending due to out of scope");
                            queue.remove();
                            continue;
                        }

                        // Check if chunk has been populated
                        if (!chunk.isPopulated()) {
                            LOGGER.debug("Chunk not populated");
                            break;
                        }

                        // Send the chunk to the client
                        this.sendWorldChunk(chunk);
                        queue.remove().releaseForConnection();
                        sent++;
                    }
                }

                if (!this.entity.getBlockUpdates().isEmpty()) {
                    for (BlockPosition position : this.entity.getBlockUpdates()) {
                        int chunkX = CoordinateUtils.fromBlockToChunk(position.getX());
                        int chunkZ = CoordinateUtils.fromBlockToChunk(position.getZ());
                        long chunkHash = CoordinateUtils.toLong(chunkX, chunkZ);
                        if (this.playerChunks.contains(chunkHash)) {
                            this.entity.getWorld().appendUpdatePackets(this, position);
                        }
                    }

                    this.entity.getBlockUpdates().clear();
                }
            }

            this.releaseSendQueue();
            this.lastUpdateDT = 0;
        }
    }

    private void releaseSendQueue() {
        // Send all queued packets

        if (this.sendQueue != null && !this.sendQueue.isEmpty()) {
            this.postProcessorExecutor.addWork(this, this.sendQueue.toArray(new Packet[0]), null);
            this.sendQueue.clear();
        }

    }

    private void updateNetwork(long currentMillis) {
        // It seems that movement is sent last, but we need it first to check if player position of other packets align
        List<PacketBuffer> packetBuffers = null;

        // Receive all waiting packets:
        EncapsulatedPacket packetData;
        while ((packetData = this.connection.receive()) != null) {
            if (packetBuffers == null) {
                packetBuffers = new ArrayList<>();
            }

            packetBuffers.add(new PacketBuffer(packetData.getPacketData()));
            packetData.release(); // The internal buffer took over
        }

        if (packetBuffers != null) {
            for (PacketBuffer buffer : packetBuffers) {
                // CHECKSTYLE:OFF
                try {
                    this.handleSocketData(currentMillis, buffer);
                } catch (Exception e) {
                    LOGGER.error("Error whilst processing packet: ", e);
                }
                // CHECKSTYLE:ON

                buffer.release();
            }
        }
    }

    /**
     * Sends the given packet to the player.
     *
     * @param packet The packet which should be send to the player
     */
    public void send(Packet packet, Consumer<Void> callback) {
        if (!(packet instanceof PacketBatch)) {
            this.postProcessorExecutor.addWork(this, new Packet[]{packet}, callback);
        } else {
            // CHECKSTYLE:OFF
            try {
                PacketBuffer buffer = new PacketBuffer(64);
                packet.serializeHeader(buffer);
                packet.serialize(buffer, this.protocolID);

                this.connection.send(PacketReliability.RELIABLE_ORDERED, packet.orderingChannel(), buffer);
            } catch (Exception e) {
                LOGGER.error("Could not serialize packet", e);
            }
            // CHECKSTYLE:ON
        }
    }

    @Override
    public void send(Packet packet) {
        this.send(packet, null);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    /**
     * Sends a world chunk to the player. This is used by world adapters in order to give the player connection
     * a chance to know once it is ready for spawning.
     *
     * @param chunkAdapter which should be sent to the client
     */
    private void sendWorldChunk(ChunkAdapter chunkAdapter) {
        this.playerChunks.add(chunkAdapter.longHashCode());
        this.loadingChunks.remove(chunkAdapter.longHashCode());
        this.addToSendQueue(chunkAdapter.createPackagedData(this.cache, this.cachingSupported));
        this.entity.getEntityVisibilityManager().updateAddedChunk(chunkAdapter);
        this.checkForSpawning();
    }

    public void checkForSpawning() {
        if (this.state == PlayerConnectionState.LOGIN && this.loadingChunks.isEmpty() && (!this.cachingSupported || this.cache.isEmpty())) {
            int spawnXChunk = CoordinateUtils.fromBlockToChunk((int) this.entity.getLocation().getX());
            int spawnZChunk = CoordinateUtils.fromBlockToChunk((int) this.entity.getLocation().getZ());

            WorldAdapter worldAdapter = this.entity.getWorld();
            worldAdapter.movePlayerToChunk(spawnXChunk, spawnZChunk, this.entity);

            this.getEntity().firstSpawn();

            this.state = PlayerConnectionState.PLAYING;

            this.entity.getLoginPerformance().setChunkEnd(this.entity.getWorld().getServer().getCurrentTickTime());
            this.entity.getLoginPerformance().print();
        }
    }

    // ========================================= PACKET HANDLERS ========================================= //

    /**
     * Handles data received directly from the player's connection.
     *
     * @param currentTimeMillis The time in millis of this tick
     * @param buffer            The buffer containing the received data
     */
    private void handleSocketData(long currentTimeMillis, PacketBuffer buffer) {
        if (buffer.getRemaining() <= 0) {
            // Malformed packet:
            return;
        }

        while (buffer.getRemaining() > 0) {
            int packetLength = buffer.readUnsignedVarInt();

            int currentIndex = buffer.getReadPosition();
            int packetID = this.handleBufferData(currentTimeMillis, buffer, currentIndex + packetLength);

            int consumedByPacket = buffer.getReadPosition() - currentIndex;
            if (consumedByPacket != packetLength) {
                int remaining = packetLength - consumedByPacket;
                LOGGER.error("Malformed batch packet payload: Could not read enclosed packet data correctly: 0x{} remaining {} bytes", Integer.toHexString(packetID), remaining);
                ReportUploader.create().tag("network.packet_remaining").property("packet_id", "0x" + Integer.toHexString(packetID)).property("packet_remaining", String.valueOf(remaining)).upload();
                return;
            }
        }
    }

    private int handleBufferData(long currentTimeMillis, PacketBuffer buffer, int skippablePosition) {
        // Grab the packet ID from the packet's data
        int rawId = buffer.readUnsignedVarInt();
        int packetId = rawId & 0x3FF;

        // There is some data behind the packet id when non batched packets (2 bytes)
        if (packetId == BATCH_MAGIC) {
            LOGGER.error("Malformed batch packet payload: Batch packets are not allowed to contain further batch packets");
            return packetId;
        }

        LOGGER.debug("Got MCPE packet {}", Integer.toHexString(packetId & 0xFF));

        // If we are still in handshake we only accept certain packets:
        if (this.state == PlayerConnectionState.HANDSHAKE) {
            if (packetId == PACKET_LOGIN) {
                // CHECKSTYLE:OFF
                try {
                    PacketLogin packet = new PacketLogin();
                    packet.deserialize(buffer, this.protocolID);
                    this.handlePacket(currentTimeMillis, packet);
                } catch (Exception e) {
                    LOGGER.error("Could not deserialize / handle packet", e);
                    ReportUploader.create().tag("network.deserialize").exception(e).upload();
                }
                // CHECKSTYLE:ON
            } else {
                LOGGER.error("Received odd packet");
            }

            // Don't allow for any other packets if we are in HANDSHAKE state:
            return packetId;
        }

        // When we are in encryption init state
        if (this.state == PlayerConnectionState.ENCRPYTION_INIT) {
            if (packetId == PACKET_ENCRYPTION_RESPONSE) {
                // CHECKSTYLE:OFF
                try {
                    this.handlePacket(currentTimeMillis, new PacketEncryptionResponse());
                } catch (Exception e) {
                    LOGGER.error("Could not deserialize / handle packet", e);
                    ReportUploader.create().tag("network.deserialize").exception(e).upload();
                }
                // CHECKSTYLE:ON
            } else {
                LOGGER.error("Received odd packet");
            }

            // Don't allow for any other packets if we are in RESOURCE_PACK state:
            return packetId;
        }

        // When we are in resource pack state
        if (this.state == PlayerConnectionState.RESOURCE_PACK) {
            if (packetId == PACKET_RESOURCEPACK_RESPONSE) {
                // CHECKSTYLE:OFF
                try {
                    PacketResourcePackResponse packet = new PacketResourcePackResponse();
                    packet.deserialize(buffer, this.protocolID);
                    this.handlePacket(currentTimeMillis, packet);
                } catch (Exception e) {
                    LOGGER.error("Could not deserialize / handle packet", e);
                    ReportUploader.create().tag("network.deserialize").exception(e).upload();
                }
                // CHECKSTYLE:ON
            } else {
                LOGGER.error("Received odd packet");
            }

            // Don't allow for any other packets if we are in RESOURCE_PACK state:
            return packetId;
        }


        Packet packet = Protocol.createPacket(packetId);
        if (packet == null) {
            this.networkManager.notifyUnknownPacket(packetId, buffer);

            // Got to skip
            buffer.setReadPosition(skippablePosition);
            return packetId;
        }

        // There are skippables, if we hit one simply forward the data stream and ignore the packet
        if (packet instanceof PacketSkipable) {
            buffer.setReadPosition(skippablePosition);
            return packetId;
        }

        // CHECKSTYLE:OFF
        try {
            packet.deserialize(buffer, this.protocolID);
            this.handlePacket(currentTimeMillis, packet);
        } catch (Exception e) {
            LOGGER.error("Could not deserialize / handle packet", e);
            ReportUploader.create().tag("network.deserialize").exception(e).upload();
        }
        // CHECKSTYLE:ON
        
        return packetId;
    }

    /**
     * Handles compressed batch packets directly by decoding their payload.
     *
     * @param buffer The buffer containing the batch packet's data (except packet ID)
     * @return decompressed and decrypted data
     */
    private ByteBuf handleBatchPacket(ByteBuf buffer) {
        return this.inputProcessor.process(buffer);
    }

    /**
     * Handles a deserialized packet by dispatching it to the appropriate handler method.
     *
     * @param currentTimeMillis The time this packet arrived at the network manager
     * @param packet            The packet to handle
     */
    @SuppressWarnings("unchecked")  // Needed for generic types not matching
    private void handlePacket(long currentTimeMillis, Packet packet) throws Exception {
        PacketHandler handler = this.networkManager.getPacketHandler(packet.getId() & 0xff);
        if (handler != null) {
            LOGGER.debug("Packet: {}", packet);
            handler.handle(packet, currentTimeMillis, this);
            return;
        }

        LOGGER.warn("No handler for {}", packet);
        ReportUploader.create().tag("network.missing_handler").property("packet", packet.getClass().getName()).upload("Missing handler for packet " + packet.getClass().getName());
    }

    /**
     * Check if we need to send new chunks to the player
     *
     * @param from                which location the entity moved
     * @param forceResendEntities should we resend all entities known?
     */
    public void checkForNewChunks(Location from, boolean forceResendEntities) {
        WorldAdapter worldAdapter = this.entity.getWorld();

        int currentXChunk = CoordinateUtils.fromBlockToChunk((int) this.entity.getLocation().getX());
        int currentZChunk = CoordinateUtils.fromBlockToChunk((int) this.entity.getLocation().getZ());

        int viewDistance = this.entity.getViewDistance();

        List<Pair<Integer, Integer>> toSendChunks = new ArrayList<>();

        for (int sendXChunk = -viewDistance; sendXChunk <= viewDistance; sendXChunk++) {
            for (int sendZChunk = -viewDistance; sendZChunk <= viewDistance; sendZChunk++) {
                float distance = MathUtils.sqrt(sendZChunk * sendZChunk + sendXChunk * sendXChunk);
                int chunkDistance = MathUtils.fastRound(distance);

                if (chunkDistance <= viewDistance) {
                    Pair<Integer, Integer> newChunk = new Pair<>(currentXChunk + sendXChunk, currentZChunk + sendZChunk);

                    if (forceResendEntities) {
                        toSendChunks.add(newChunk);
                    } else {
                        long hash = CoordinateUtils.toLong(newChunk.getFirst(), newChunk.getSecond());
                        if (!this.playerChunks.contains(hash) && !this.loadingChunks.contains(hash)) {
                            toSendChunks.add(newChunk);
                        }
                    }
                }
            }
        }

        // Sort so that chunks closer to the current chunk may be sent first
        toSendChunks.sort((o1, o2) -> {
            if (Objects.equals(o1.getFirst(), o2.getFirst()) &&
                Objects.equals(o1.getSecond(), o2.getSecond())) {
                return 0;
            }

            int distXFirst = Math.abs(o1.getFirst() - currentXChunk);
            int distXSecond = Math.abs(o2.getFirst() - currentXChunk);

            int distZFirst = Math.abs(o1.getSecond() - currentZChunk);
            int distZSecond = Math.abs(o2.getSecond() - currentZChunk);

            if (distXFirst + distZFirst > distXSecond + distZSecond) {
                return 1;
            } else if (distXFirst + distZFirst < distXSecond + distZSecond) {
                return -1;
            }

            return 0;
        });

        if (forceResendEntities) {
            this.entity.getEntityVisibilityManager().clear();
        }

        for (Pair<Integer, Integer> chunk : toSendChunks) {
            long hash = CoordinateUtils.toLong(chunk.getFirst(), chunk.getSecond());
            if (forceResendEntities) {
                if (!this.playerChunks.contains(hash) && !this.loadingChunks.contains(hash)) {
                    this.loadingChunks.add(hash);
                    this.requestChunk(chunk.getFirst(), chunk.getSecond());
                } else {
                    // We already know this chunk but maybe forceResend is enabled
                    worldAdapter.sendChunk(chunk.getFirst(), chunk.getSecond(), (chunkHash, loadedChunk) -> {
                            if (this.entity != null) { // It can happen that the server loads longer and the client has disconnected
                                this.entity.getEntityVisibilityManager().updateAddedChunk(loadedChunk);
                            }
                        });
                }
            } else {
                this.loadingChunks.add(hash);
                this.requestChunk(chunk.getFirst(), chunk.getSecond());
            }
        }

        // Move the player to this chunk
        if (from != null) {
            int oldChunkX = CoordinateUtils.fromBlockToChunk((int) from.getX());
            int oldChunkZ = CoordinateUtils.fromBlockToChunk((int) from.getZ());
            if (!from.getWorld().equals(worldAdapter) || oldChunkX != currentXChunk || oldChunkZ != currentZChunk) {
                worldAdapter.movePlayerToChunk(currentXChunk, currentZChunk, this.entity);
                this.sendNetworkChunkPublisher();
            }
        }

        boolean unloaded = false;

        // Check for unloading chunks
        LongIterator longCursor = this.playerChunks.iterator();
        while (longCursor.hasNext()) {
            long hash = longCursor.nextLong();
            int x = (int) (hash >> 32);
            int z = (int) (hash) + Integer.MIN_VALUE;

            if (Math.abs(x - currentXChunk) > viewDistance ||
                Math.abs(z - currentZChunk) > viewDistance) {
                ChunkAdapter chunk = this.entity.getWorld().getChunk(x, z);
                if (chunk == null) {
                    LOGGER.error("Wanted to update state on already unloaded chunk {} {}", x, z);
                } else {
                    // TODO: Check for Packets to send to the client to unload the chunk?
                    this.entity.getEntityVisibilityManager().updateRemoveChunk(chunk);
                }

                unloaded = true;
                longCursor.remove();
            }
        }

        longCursor = this.loadingChunks.iterator();
        while (longCursor.hasNext()) {
            long hash = longCursor.nextLong();
            int x = (int) (hash >> 32);
            int z = (int) (hash) + Integer.MIN_VALUE;

            if (Math.abs(x - currentXChunk) > viewDistance ||
                Math.abs(z - currentZChunk) > viewDistance) {
                longCursor.remove(); // Not needed anymore
            }
        }

        if (unloaded || !this.entity.getChunkSendQueue().isEmpty()) {
            this.sendNetworkChunkPublisher();
        }
    }

    public void sendNetworkChunkPublisher() {
        PacketNetworkChunkPublisherUpdate packetNetworkChunkPublisherUpdate = new PacketNetworkChunkPublisherUpdate();
        packetNetworkChunkPublisherUpdate.setBlockPosition(this.entity.getLocation().toBlockPosition());
        packetNetworkChunkPublisherUpdate.setRadius(this.entity.getViewDistance() * 16);
        this.addToSendQueue(packetNetworkChunkPublisherUpdate);
    }

    private void requestChunk(Integer x, Integer z) {
        LOGGER.debug("Requesting chunk {} {} for {}", x, z, this.entity);
        this.entity.getWorld().sendChunk(x, z, (chunkHash, loadedChunk) -> {
                LOGGER.debug("Loaded chunk: {} -> {}", this.entity, loadedChunk);
                if (this.entity != null) { // It can happen that the server loads longer and the client has disconnected
                    loadedChunk.retainForConnection();
                    if (!this.entity.getChunkSendQueue().offer(loadedChunk)) {
                        LOGGER.warn("Could not add chunk to send queue");
                        loadedChunk.releaseForConnection();
                    }

                    LOGGER.debug("Current queue length: {}", this.entity.getChunkSendQueue().size());
                }
            });
    }

    /**
     * Send resource packs
     */
    public void initWorldAndResourceSend() {
        // We have the chance of forcing resource and behaviour packs here
        PacketResourcePacksInfo packetResourcePacksInfo = new PacketResourcePacksInfo();
        this.addToSendQueue(packetResourcePacksInfo);
    }

    /**
     * Send chunk radius
     */
    private void sendChunkRadiusUpdate() {
        PacketConfirmChunkRadius packetConfirmChunkRadius = new PacketConfirmChunkRadius();
        packetConfirmChunkRadius.setChunkRadius(this.entity.getViewDistance());
        this.send(packetConfirmChunkRadius);
    }

    /**
     * Disconnect (kick) the player with a custom message
     *
     * @param message The message with which the player is going to be kicked
     */
    public void disconnect(String message) {
        this.networkManager.getServer().getPluginManager().callEvent(new PlayerKickEvent(this.entity, message));

        if (message != null && message.length() > 0) {
            PacketDisconnect packet = new PacketDisconnect();
            packet.setMessage(message);
            this.send(packet);

            this.server.getExecutorService().schedule(() -> PlayerConnection.this.internalClose(message), 3, TimeUnit.SECONDS);
        } else {
            this.internalClose(message);
        }

        if (this.entity != null) {
            LOGGER.info("EntityPlayer {} left the game: {}", this.entity.getName(), message);
        } else {
            LOGGER.info("EntityPlayer has been disconnected whilst logging in: {}", message);
        }
    }

    private void internalClose(String message) {
        if (this.connection.isConnected() && !this.connection.isDisconnecting()) {
            this.connection.disconnect(message);
        }
    }

    // ====================================== PACKET SENDERS ====================================== //

    /**
     * Sends a PacketPlayState with the specified state to this player.
     *
     * @param state The state to send
     */
    public void sendPlayState(PacketPlayState.PlayState state) {
        PacketPlayState packet = new PacketPlayState();
        packet.setState(state);
        this.addToSendQueue(packet);
    }

    /**
     * Sends the player a move player packet which will teleport him to the
     * given location.
     *
     * @param location The location to teleport the player to
     */
    public void sendMovePlayer(Location location) {
        PacketMovePlayer move = new PacketMovePlayer();
        move.setEntityId(this.entity.getEntityId());
        move.setX(location.getX());
        move.setY((float) (location.getY() + 1.62));
        move.setZ(location.getZ());
        move.setHeadYaw(location.getHeadYaw());
        move.setYaw(location.getYaw());
        move.setPitch(location.getPitch());
        move.setMode(MovePlayerMode.TELEPORT);
        move.setOnGround(this.getEntity().isOnGround());
        move.setRidingEntityId(0);    // TODO: Implement riding entities correctly
        move.setTick(this.entity.getWorld().getServer().getCurrentTickTime() / 50);
        this.addToSendQueue(move);
    }

    /**
     * Sends the player the specified time as world time. The original client sends
     * the current world time every 256 ticks in order to synchronize all client's world
     * times.
     *
     * @param ticks The current number of ticks of the world time
     */
    public void sendWorldTime(int ticks) {
        PacketWorldTime time = new PacketWorldTime();
        time.setTicks(ticks);
        this.addToSendQueue(time);
    }

    /**
     * Sends a world initialization packet of the world the entity associated with this
     * connection is currently in to this player.
     */
    public void sendWorldInitialization(long entityId) {
        WorldAdapter world = this.entity.getWorld();

        PacketStartGame packet = new PacketStartGame();
        packet.setEntityId(entityId);
        packet.setRuntimeEntityId(entityId);
        packet.setGamemode(EnumConnectors.GAMEMODE_CONNECTOR.convert(this.entity.getGamemode()).getMagicNumber());

        Location spawn = this.entity.getSpawnLocation() != null ? this.entity.getSpawnLocation() : world.getSpawnLocation();

        packet.setLocation(this.entity.getLocation());
        packet.setSpawn(spawn);

        packet.setWorldGamemode(0);
        packet.setDayCycleStopTime(world.getTimeAsTicks());

        Biome biome = world.getBiome(spawn.toBlockPosition());
        packet.setBiomeType(PacketStartGame.BIOME_TYPE_DEFAULT);
        packet.setBiomeName(biome.getName());
        packet.setDimension(0);

        packet.setSeed(12345);
        packet.setGenerator(1);
        packet.setDifficulty(this.entity.getWorld().getDifficulty().getDifficultyDegree());
        packet.setLevelId(Base64.getEncoder().encodeToString(StringUtil.getUTF8Bytes(world.getWorldName())));
        packet.setWorldName(world.getWorldName());
        packet.setTemplateId("");
        packet.setGamerules(world.getGamerules());
        packet.setTexturePacksRequired(false);
        packet.setCommandsEnabled(true);
        packet.setEnchantmentSeed(ThreadLocalRandom.current().nextInt());
        packet.setCorrelationId(this.server.getServerUniqueID().toString());

        packet.setBlockPalette(this.server.getBlocks().getPacketCache());
        packet.setItemPalette(this.server.getItems().getPacketCache());

        // Set the new location
        this.addToSendQueue(packet);
    }

    /**
     * The underlying RakNet Connection closed. Cleanup
     */
    void close() {
        LOGGER.info("Player {} disconnected", this.entity);

        if (this.entity != null && this.entity.getWorld() != null) {
            PlayerQuitEvent event = this.networkManager.getServer().getPluginManager().callEvent(new PlayerQuitEvent(this.entity, ChatColor.YELLOW + this.entity.getDisplayName() + " left the game."));
            if (event.getQuitMessage() != null && !event.getQuitMessage().isEmpty()) {
                this.getServer().getPlayers().forEach((player) -> {
                    player.sendMessage(event.getQuitMessage());
                });
            }
            this.entity.getWorld().removePlayer(this.entity);
            this.entity.cleanup();
            this.entity.setDead(true);
            this.networkManager.getServer().getPluginManager().callEvent(new PlayerCleanedupEvent(this.entity));

            if (this.entity.hasCompletedLogin()) {
                this.entity.getWorld().persistPlayer(this.entity);
            }

            this.entity = null;
        }

        if (this.postProcessorExecutor != null) {
            this.networkManager.getPostProcessService().releaseExecutor(this.postProcessorExecutor);
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
     * @return ping of UDP connection
     */
    public int getPing() {
        return (int) this.connection.getPing();
    }

    public long getId() {
        return this.connection.getGuid();
    }

    @Override
    public String toString() {
        return this.entity != null ? this.entity.getName() : (this.connection != null) ? String.valueOf(this.connection.getGuid()) : "unknown";
    }

    public void sendPlayerSpawnPosition() {
        PacketSetSpawnPosition spawnPosition = new PacketSetSpawnPosition();
        spawnPosition.setSpawnType(PacketSetSpawnPosition.SpawnType.PLAYER);
        spawnPosition.setPlayerPosition(this.getEntity().getPosition().toBlockPosition());
        spawnPosition.setDimension(this.entity.getWorld().getDimension());
        spawnPosition.setWorldSpawn(this.getEntity().getWorld().getSpawnLocation().toBlockPosition());
        addToSendQueue(spawnPosition);
    }

    public void sendSpawnPosition() {
        PacketSetSpawnPosition spawnPosition = new PacketSetSpawnPosition();
        spawnPosition.setSpawnType(PacketSetSpawnPosition.SpawnType.WORLD);
        spawnPosition.setPlayerPosition(this.getEntity().getPosition().toBlockPosition());
        spawnPosition.setDimension(this.entity.getWorld().getDimension());
        spawnPosition.setWorldSpawn(this.getEntity().getWorld().getSpawnLocation().toBlockPosition());
        addToSendQueue(spawnPosition);
    }

    public void sendDifficulty() {
        PacketSetDifficulty setDifficulty = new PacketSetDifficulty();
        setDifficulty.setDifficulty(this.entity.getWorld().getDifficulty().getDifficultyDegree());
        addToSendQueue(setDifficulty);
    }

    public void sendCommandsEnabled() {
        PacketSetCommandsEnabled setCommandsEnabled = new PacketSetCommandsEnabled();
        setCommandsEnabled.setEnabled(true);
        addToSendQueue(setCommandsEnabled);
    }

    public void resetQueuedChunks() {
        if (!this.entity.getChunkSendQueue().isEmpty()) {
            for (ChunkAdapter adapter : this.entity.getChunkSendQueue()) {
                long hash = CoordinateUtils.toLong(adapter.getX(), adapter.getZ());
                this.loadingChunks.remove(hash);
                adapter.releaseForConnection();
            }
        }

        this.entity.getChunkSendQueue().clear();
    }

    public void spawnPlayerEntities() {
        // Now its ok to send players
        this.entity.setSpawnPlayers(true);

        // Send player list for all online players
        List<PacketPlayerlist.Entry> listEntry = null;
        for (io.gomint.entity.EntityPlayer player : this.getServer().getPlayers()) {
            if (!this.entity.isHidden(player) && !this.entity.equals(player)) {
                if (listEntry == null) {
                    listEntry = new ArrayList<>();
                }

                listEntry.add(new PacketPlayerlist.Entry((EntityPlayer) player));
            }
        }

        if (listEntry != null) {
            // Send player list
            PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
            packetPlayerlist.setMode((byte) 0);
            packetPlayerlist.setEntries(listEntry);
            this.send(packetPlayerlist);
        }

        // Show all players
        LongIterator playerChunksIterator = this.playerChunks.iterator();
        while (playerChunksIterator.hasNext()) {
            long chunkHash = playerChunksIterator.nextLong();

            int currentX = (int) (chunkHash >> 32);
            int currentZ = (int) (chunkHash) + Integer.MIN_VALUE;

            ChunkAdapter chunk = this.entity.getWorld().getChunk(currentX, currentZ);
            this.entity.getEntityVisibilityManager().updateAddedChunk(chunk);
        }
    }

    public boolean knowsChunk(long hash) {
        return this.playerChunks.contains(hash);
    }

}
