/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import com.koloboke.collect.LongCursor;
import com.koloboke.function.LongObjConsumer;
import io.gomint.event.network.PingEvent;
import io.gomint.event.player.PlayerPreLoginEvent;
import io.gomint.jraknet.*;
import io.gomint.server.GoMintServer;
import io.gomint.server.network.tcp.ConnectionHandler;
import io.gomint.server.network.tcp.Initializer;
import io.gomint.server.util.collection.GUIDSet;
import io.gomint.server.util.collection.PlayerConnectionMap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.util.ResourceLeakDetector;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public class NetworkManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( NetworkManager.class );
    private final GoMintServer server;

    // Connections which were closed and should be removed during next tick:
    private final GUIDSet closedConnections = GUIDSet.withExpectedSize( 5 );
    private ServerSocket socket;
    private PlayerConnectionMap playersByGuid = PlayerConnectionMap.withExpectedSize( 20 );

    // TCP listener
    private ServerBootstrap tcpListener;
    private Channel tcpChannel;
    private AtomicLong idCounter = new AtomicLong( 0 );
    private int boundPort = 0;

    // Incoming connections to be added to the player map during next tick:
    private Queue<PlayerConnection> incomingConnections = new ConcurrentLinkedQueue<>();

    // Packet Dumping
    private boolean dump;
    private File dumpDirectory;

    // Motd
    @Getter @Setter
    private String motd;

    // Internal ticking
    private long currentTickMillis;
    private float lastTickTime;
    private final LongObjConsumer<PlayerConnection> connectionConsumer = new LongObjConsumer<PlayerConnection>() {
        @Override
        public void accept( long l, PlayerConnection connection ) {
            connection.update( currentTickMillis, lastTickTime );
        }
    };

    // Post process service
    @Getter
    private ExecutorService postProcessService = Executors.newSingleThreadExecutor();

    /**
     * Init a new NetworkManager for accepting new connections and read incoming data
     *
     * @param server server instance which should be used
     */
    public NetworkManager( GoMintServer server ) {
        this.server = server;
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
    public void initialize( int maxConnections, String host, int port ) throws SocketException {
        System.setProperty( "java.net.preferIPv4Stack", "true" );               // We currently don't use ipv6
        System.setProperty( "io.netty.selectorAutoRebuildThreshold", "0" );     // Never rebuild selectors
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );   // Eats performance

        // Check which listener to use
        if ( this.server.getServerConfig().getListener().isUseTCP() ) {
            this.tcpListener = Initializer.buildServerBootstrap( connectionHandler -> {
                PlayerPreLoginEvent playerPreLoginEvent = getServer().getPluginManager().callEvent(
                    new PlayerPreLoginEvent( (InetSocketAddress) connectionHandler.getChannel().remoteAddress() )
                );

                if ( playerPreLoginEvent.isCancelled() ) {
                    connectionHandler.disconnect();
                    return;
                }

                PlayerConnection playerConnection = new PlayerConnection( NetworkManager.this, null,
                    connectionHandler, PlayerConnectionState.HANDSHAKE );
                playerConnection.setTcpId( idCounter.incrementAndGet() );

                incomingConnections.offer( playerConnection );

                connectionHandler.onPing( playerConnection::setTcpPing );
                connectionHandler.whenDisconnected( aVoid -> handleConnectionClosed( playerConnection.getId() ) );
            } );

            this.tcpChannel = this.tcpListener.bind( host, port ).syncUninterruptibly().channel();
            this.boundPort = port;
        } else {
            if ( this.socket != null ) {
                throw new IllegalStateException( "Cannot re-initialize network manager" );
            }

            System.setProperty( "java.net.preferIPv4Stack", "true" );               // We currently don't use ipv6
            System.setProperty( "io.netty.selectorAutoRebuildThreshold", "0" );     // Never rebuild selectors
            ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );   // Eats performance

            this.socket = new ServerSocket( maxConnections );
            this.socket.setMojangModificationEnabled( true );
            this.socket.setEventLoopFactory( this.server.getThreadFactory() );
            this.socket.setEventHandler( ( socket, socketEvent ) -> NetworkManager.this.handleSocketEvent( socketEvent ) );
            this.socket.bind( host, port );
        }
    }

    /**
     * Sets whether or not unknown packets should be dumped.
     *
     * @param dump Whether or not to enable packet dumping
     */
    public void setDumpingEnabled( boolean dump ) {
        this.dump = dump;
    }

    /**
     * Sets the directory where packet dump should be written to if dumping is enabled.
     *
     * @param dumpDirectory The directory to write packet dumps into
     */
    public void setDumpDirectory( File dumpDirectory ) {
        this.dumpDirectory = dumpDirectory;
    }

    /**
     * Ticks the network manager, i.e. updates all player connections and handles all incoming
     * data packets.
     *
     * @param currentMillis The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param lastTickTime  The delta from the full second which has been calculated in the last tick
     */
    public void update( long currentMillis, float lastTickTime ) {
        // Handle updates to player map:
        while ( !this.incomingConnections.isEmpty() ) {
            PlayerConnection connection = this.incomingConnections.poll();
            this.playersByGuid.justPut( connection.getId(), connection );
        }

        synchronized ( this.closedConnections ) {
            if ( !this.closedConnections.isEmpty() ) {
                LongCursor cursor = this.closedConnections.cursor();
                while ( cursor.moveNext() ) {
                    long guid = cursor.elem();
                    PlayerConnection connection = this.playersByGuid.remove( guid );
                    if ( connection != null ) {
                        connection.close();
                    }
                }

                this.closedConnections.clear();
            }
        }

        // Tick all player connections in order to receive all incoming packets:
        this.currentTickMillis = currentMillis;
        this.lastTickTime = lastTickTime;
        this.playersByGuid.forEach( this.connectionConsumer );
    }

    /**
     * Closes the network manager and all player connections.
     */
    public void close() {
        if ( this.socket != null ) {
            this.socket.close();
            this.socket = null;

            this.playersByGuid.forEach( new LongObjConsumer<PlayerConnection>() {
                @Override
                public void accept( long l, PlayerConnection playerConnection ) {
                    playerConnection.close();
                }
            } );
        }

        if ( this.tcpListener != null ) {
            this.tcpChannel.close();
            Initializer.close();
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
    void notifyUnknownPacket( byte packetId, PacketBuffer buffer ) {
        if ( this.dump ) {
            LOGGER.info( "Received unknown packet 0x" + Integer.toHexString( ( (int) packetId ) & 0xFF ) );
            this.dumpPacket( packetId, buffer );
        }
    }

    // ======================================== SOCKET HANDLERS ======================================== //

    /**
     * Handles the given socket event.
     *
     * @param event The event that was received
     */
    private void handleSocketEvent( SocketEvent event ) {
        switch ( event.getType() ) {
            case NEW_INCOMING_CONNECTION:
                PlayerPreLoginEvent playerPreLoginEvent = this.getServer().getPluginManager().callEvent(
                        new PlayerPreLoginEvent( event.getConnection().getAddress() )
                );

                if ( playerPreLoginEvent.isCancelled() ) {
                    // Since the user has not gotten any packets we are not able to be sure if we can send him a disconnect notification
                    // so we decide to close the raknet connection without any notice
                    event.getConnection().disconnect( null );
                    return;
                }

                this.handleNewConnection( event.getConnection() );
                break;

            case CONNECTION_CLOSED:
            case CONNECTION_DISCONNECTED:
                this.handleConnectionClosed( event.getConnection().getGuid() );
                break;

            case UNCONNECTED_PING:
                this.handleUnconnectedPing( event );
                break;

            default:
                break;
        }
    }

    private void handleUnconnectedPing( SocketEvent event ) {
        // Fire ping event so plugins can modify the motd and player amounts
        PingEvent pingEvent = this.server.getPluginManager().callEvent(
                new PingEvent(
                    this.server.getMotd(),
                    this.server.getAmountOfPlayers(),
                    this.server.getServerConfig().getMaxPlayers()
                )
        );

        event.getPingPongInfo().setMotd( "MCPE;" + pingEvent.getMotd() + ";" + Protocol.MINECRAFT_PE_PROTOCOL_VERSION +
            ";" + Protocol.MINECRAFT_PE_NETWORK_VERSION + ";" + pingEvent.getOnlinePlayers() + ";" + pingEvent.getMaxPlayers() );
    }

    /**
     * Handles a new incoming connection.
     *
     * @param connection The new incoming connection
     */
    private void handleNewConnection( Connection connection ) {
        this.incomingConnections.add( new PlayerConnection( this, connection, null, PlayerConnectionState.HANDSHAKE ) );
    }

    /**
     * Handles a connection that just got closed.
     *
     * @param id of the connection being closed
     */
    private void handleConnectionClosed( long id ) {
        synchronized ( this.closedConnections ) {
            this.closedConnections.add( id );
        }
    }

    private void dumpPacket( byte packetId, PacketBuffer buffer ) {
        LOGGER.info( "Dumping packet " + Integer.toHexString( ( (int) packetId ) & 0xFF ) );

        StringBuilder filename = new StringBuilder( Integer.toHexString( ( (int) packetId ) & 0xFF ) );
        while ( filename.length() < 2 ) {
            filename.insert( 0, "0" );
        }

        filename.append( "_" ).append( System.currentTimeMillis() );
        filename.append( ".dump" );

        File dumpFile = new File( this.dumpDirectory, filename.toString() );

        // Dump buffer contents:
        try ( OutputStream out = new FileOutputStream( dumpFile ) ) {
            try ( BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( out ) ) ) {
                writer.write( "# Packet dump of 0x" + Integer.toHexString( ( (int) packetId ) & 0xFF ) + "\n" );
                writer.write( "-------------------------------------\n" );
                writer.write( "# Textual payload\n" );
                StringBuilder lineBuilder = new StringBuilder();
                while ( buffer.getRemaining() > 0 ) {
                    for ( int i = 0; i < 16 && buffer.getRemaining() > 0; ++i ) {
                        String hex = Integer.toHexString( ( (int) buffer.readByte() ) & 0xFF );
                        if ( hex.length() < 2 ) {
                            hex = "0" + hex;
                        }
                        lineBuilder.append( hex );
                        if ( i + 1 < 16 && buffer.getRemaining() > 0 ) {
                            lineBuilder.append( " " );
                        }
                    }
                    lineBuilder.append( "\n" );

                    writer.write( lineBuilder.toString() );
                    lineBuilder = new StringBuilder();
                }
                writer.write( "-------------------------------------\n" );
                writer.write( "# Binary payload\n" );
                writer.flush();

                buffer.resetPosition();
                buffer.skip( 1 ); // Packet ID
                out.write( buffer.getBuffer(), buffer.getPosition(), buffer.getRemaining() );
            }
        } catch ( IOException e ) {
            LOGGER.error( "Failed to dump packet " + filename );
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

}
