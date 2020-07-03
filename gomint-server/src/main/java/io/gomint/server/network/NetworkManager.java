/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.event.network.PingEvent;
import io.gomint.event.player.PlayerPreLoginEvent;
import io.gomint.jraknet.Connection;
import io.gomint.jraknet.EventLoops;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.server.GoMintServer;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.network.tcp.ConnectionHandler;
import io.gomint.server.network.tcp.Initializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ThreadDeathWatcher;
import io.netty.util.concurrent.GlobalEventExecutor;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
@Component
public class NetworkManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);
    private final GoMintServer server;

    private final AnnotationConfigApplicationContext context;

    // Connections which were closed and should be removed during next tick:
    private final LongSet closedConnections = new LongOpenHashSet();
    private ServerSocket socket;
    private Long2ObjectMap<PlayerConnection> playersByGuid = new Long2ObjectOpenHashMap<>();

    // TCP listener
    private ServerBootstrap tcpListener;
    private Channel tcpChannel;
    private AtomicLong idCounter = new AtomicLong(0);
    private int boundPort = 0;

    // Incoming connections to be added to the player map during next tick:
    private Queue<PlayerConnection> incomingConnections = new ConcurrentLinkedQueue<>();

    // Packet Dumping
    private boolean dump;
    private File dumpDirectory;

    // Motd
    @Getter
    @Setter
    private String motd;

    // Post process service
    @Getter
    private PostProcessExecutorService postProcessService;

    /**
     * Init a new NetworkManager for accepting new connections and read incoming data
     *
     * @param context which started the application
     * @param server  server instance which should be used
     */
    @Autowired
    public NetworkManager(AnnotationConfigApplicationContext context, GoMintServer server) {
        this.context = context;
        this.server = server;
        this.postProcessService = new PostProcessExecutorService(server.getExecutorService());
    }

    // ======================================= PUBLIC API ======================================= //

    /**
     * Initializes the network manager and its underlying server socket.
     *
     * @param maxConnections The maximum number of players expected to join the server
     * @param host           The hostname the internal socket should be bound to
     * @param port           The port the internal socket should be bound to
     * @throws SocketException Thrown if any the internal socket could not be bound
     */
    public void initialize(int maxConnections, String host, int port) throws SocketException {
        System.setProperty("java.net.preferIPv4Stack", "true");               // We currently don't use ipv6
        System.setProperty("io.netty.selectorAutoRebuildThreshold", "0");     // Never rebuild selectors
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);   // Eats performance

        // Check which listener to use
        if (this.server.getServerConfig().getListener().isUseTCP()) {
            this.tcpListener = Initializer.buildServerBootstrap(connectionHandler -> {
                PlayerPreLoginEvent playerPreLoginEvent = getServer().getPluginManager().callEvent(
                    new PlayerPreLoginEvent((InetSocketAddress) connectionHandler.getChannel().remoteAddress())
                );

                if (playerPreLoginEvent.isCancelled()) {
                    connectionHandler.disconnect();
                    return;
                }

                this.context.registerBean("network.newConnection.raknet", Connection.class, () -> null);
                this.context.registerBean("network.newConnection.tcp", ConnectionHandler.class, () -> connectionHandler);

                PlayerConnection playerConnection = this.context.getAutowireCapableBeanFactory().getBean(PlayerConnection.class);
                playerConnection.setTcpId(idCounter.incrementAndGet());

                this.context.removeBeanDefinition("network.newConnection.raknet");
                this.context.removeBeanDefinition("network.newConnection.tcp");

                incomingConnections.offer(playerConnection);

                connectionHandler.onPing(playerConnection::setTcpPing);
                connectionHandler.whenDisconnected(aVoid -> handleConnectionClosed(playerConnection.getId()));
                connectionHandler.onException(throwable -> LOGGER.warn("Exception in TCP handling", throwable));
            });

            this.tcpChannel = this.tcpListener.bind(host, port).syncUninterruptibly().channel();
            this.boundPort = port;
        } else {
            if (this.socket != null) {
                throw new IllegalStateException("Cannot re-initialize network manager");
            }

            this.socket = new ServerSocket(LOGGER, maxConnections);
            this.socket.setMojangModificationEnabled(true);
            this.socket.setEventHandler((eventSocket, socketEvent) -> NetworkManager.this.handleSocketEvent(socketEvent));
            this.socket.bind(host, port);
        }
    }

    /**
     * Sets whether or not unknown packets should be dumped.
     *
     * @param dump Whether or not to enable packet dumping
     */
    public void setDumpingEnabled(boolean dump) {
        this.dump = dump;
    }

    /**
     * Sets the directory where packet dump should be written to if dumping is enabled.
     *
     * @param dumpDirectory The directory to write packet dumps into
     */
    public void setDumpDirectory(File dumpDirectory) {
        this.dumpDirectory = dumpDirectory;
    }

    /**
     * Ticks the network manager, i.e. updates all player connections and handles all incoming
     * data packets.
     *
     * @param currentMillis The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param lastTickTime  The delta from the full second which has been calculated in the last tick
     */
    public void update(long currentMillis, float lastTickTime) {
        // Handle updates to player map:
        while (!this.incomingConnections.isEmpty()) {
            PlayerConnection connection = this.incomingConnections.poll();
            if (connection != null) {
                LOGGER.debug("Adding new connection to the server: {}", connection);
                this.playersByGuid.put(connection.getId(), connection);
            }
        }

        synchronized (this.closedConnections) {
            if (!this.closedConnections.isEmpty()) {
                for (long guid : this.closedConnections) {
                    PlayerConnection connection = this.playersByGuid.remove(guid);
                    if (connection != null) {
                        connection.close();
                    }
                }

                this.closedConnections.clear();
            }
        }

        // Tick all player connections in order to receive all incoming packets:
        for (Long2ObjectMap.Entry<PlayerConnection> entry : this.playersByGuid.long2ObjectEntrySet()) {
            entry.getValue().update(currentMillis, lastTickTime);
        }
    }

    /**
     * Closes the network manager and all player connections.
     */
    public void close() {
        // Close the jRaknet EventLoops, we don't need them anymore
        try {
            EventLoops.cleanup();

            GlobalEventExecutor.INSTANCE.awaitInactivity(5, TimeUnit.SECONDS);
            ThreadDeathWatcher.awaitInactivity(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Could not shutdown netty loops", e);
            Thread.currentThread().interrupt();
        }
    }

    // ======================================= INTERNALS ======================================= //

    /**
     * Gets the GoMint server instance that created this network manager.
     *
     * @return The GoMint server instance that created this network manager
     */
    public GoMintServer getServer() {
        return this.server;
    }

    /**
     * Invoked by a player connection whenever it encounters a packet it may not decompose.
     *
     * @param packetId The ID of the packet
     * @param buffer   The packet's contents without its ID
     */
    void notifyUnknownPacket(int packetId, PacketBuffer buffer) {
        ReportUploader.create().property("network.unknown_packet", "0x" + Integer.toHexString(packetId & 0xFF)).upload("Unknown packet 0x" + Integer.toHexString(((int) packetId) & 0xFF));

        if (this.dump) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Received unknown packet 0x{}", Integer.toHexString(packetId & 0xFF));
            }

            this.dumpPacket(packetId, buffer);
        }
    }

    // ======================================== SOCKET HANDLERS ======================================== //

    /**
     * Handles the given socket event.
     *
     * @param event The event that was received
     */
    private void handleSocketEvent(SocketEvent event) {
        switch (event.getType()) {
            case NEW_INCOMING_CONNECTION:
                PlayerPreLoginEvent playerPreLoginEvent = this.getServer().getPluginManager().callEvent(
                    new PlayerPreLoginEvent(event.getConnection().getAddress())
                );

                if (playerPreLoginEvent.isCancelled()) {
                    // Since the user has not gotten any packets we are not able to be sure if we can send him a disconnect notification
                    // so we decide to close the raknet connection without any notice
                    event.getConnection().disconnect(null);
                    return;
                }

                this.handleNewConnection(event.getConnection());
                break;

            case CONNECTION_CLOSED:
            case CONNECTION_DISCONNECTED:
                this.handleConnectionClosed(event.getConnection().getGuid());
                break;

            case UNCONNECTED_PING:
                this.handleUnconnectedPing(event);
                break;

            default:
                break;
        }
    }

    private void handleUnconnectedPing(SocketEvent event) {
        // Fire ping event so plugins can modify the motd and player amounts
        PingEvent pingEvent = this.server.getPluginManager().callEvent(
            new PingEvent(
                this.server.getMotd(),
                this.server.getAmountOfPlayers(),
                this.server.getServerConfig().getMaxPlayers()
            )
        );

        event.getPingPongInfo().setMotd("MCPE;" + pingEvent.getMotd() + ";" + Protocol.MINECRAFT_PE_PROTOCOL_VERSION +
            ";" + Protocol.MINECRAFT_PE_NETWORK_VERSION + ";" + pingEvent.getOnlinePlayers() + ";" + pingEvent.getMaxPlayers());
    }

    /**
     * Handles a new incoming connection.
     *
     * @param newConnection The new incoming connection
     */
    private void handleNewConnection(Connection newConnection) {
        this.context.registerBean("network.newConnection.raknet", Connection.class, () -> newConnection);
        this.context.registerBean("network.newConnection.tcp", ConnectionHandler.class, () -> null);

        PlayerConnection playerConnection = this.context.getAutowireCapableBeanFactory().getBean(PlayerConnection.class);
        this.incomingConnections.add(playerConnection);

        this.context.removeBeanDefinition("network.newConnection.raknet");
        this.context.removeBeanDefinition("network.newConnection.tcp");
    }

    /**
     * Handles a connection that just got closed.
     *
     * @param id of the connection being closed
     */
    private void handleConnectionClosed(long id) {
        synchronized (this.closedConnections) {
            this.closedConnections.add(id);
        }
    }

    private void dumpPacket(int packetId, PacketBuffer buffer) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Dumping packet {}", Integer.toHexString(packetId));
        }

        StringBuilder filename = new StringBuilder(Integer.toHexString(packetId));
        while (filename.length() < 2) {
            filename.insert(0, "0");
        }

        filename.append("_").append(System.currentTimeMillis());
        filename.append(".dump");

        File dumpFile = new File(this.dumpDirectory, filename.toString());

        // Dump buffer contents:
        try (OutputStream out = new FileOutputStream(dumpFile)) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
                writer.write("# Packet dump of 0x" + Integer.toHexString(packetId) + "\n");
                writer.write("-------------------------------------\n");
                writer.write("# Textual payload\n");
                StringBuilder lineBuilder = new StringBuilder();

                int pos = buffer.getReadPosition();
                while (buffer.getRemaining() > 0) {
                    for (int i = 0; i < 16 && buffer.getRemaining() > 0; ++i) {
                        String hex = Integer.toHexString(((int) buffer.readByte()) & 0xFF);
                        if (hex.length() < 2) {
                            hex = "0" + hex;
                        }
                        lineBuilder.append(hex);
                        if (i + 1 < 16 && buffer.getRemaining() > 0) {
                            lineBuilder.append(" ");
                        }
                    }
                    lineBuilder.append("\n");

                    writer.write(lineBuilder.toString());
                    lineBuilder = new StringBuilder();
                }
                writer.write("-------------------------------------\n");
                writer.write("# Binary payload\n");
                writer.flush();

                buffer.setReadPosition(pos);
                byte[] data = new byte[buffer.getRemaining()];
                buffer.readBytes(data);
                out.write(data);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to dump packet " + filename);
        }
    }

    /**
     * Get the port this server has bound to
     *
     * @return bound port
     */
    public int getPort() {
        return this.tcpListener != null ? this.boundPort : this.socket.getBindAddress().getPort();
    }

    /**
     * Shut all network listeners down
     */
    public void shutdown() {
        LOGGER.info("Shutting down networking");
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;

            for (Long2ObjectMap.Entry<PlayerConnection> entry : this.playersByGuid.long2ObjectEntrySet()) {
                entry.getValue().close();
            }
        }

        if (this.tcpListener != null) {
            this.tcpChannel.close();
            Initializer.close();
        }
        LOGGER.info("Shutdown of network completed");
    }

}
