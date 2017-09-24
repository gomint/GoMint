/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.event.network.PingEvent;
import io.gomint.event.player.PlayerPreLoginEvent;
import io.gomint.jraknet.*;
import io.gomint.server.GoMintServer;
import io.gomint.server.network.packet.Packet;
import lombok.Getter;
import lombok.Setter;
import com.koloboke.collect.LongCursor;
import com.koloboke.collect.map.LongObjCursor;
import com.koloboke.collect.map.LongObjMap;
import com.koloboke.collect.map.hash.HashLongObjMaps;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;
import com.koloboke.function.LongObjConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public class NetworkManager {

    private final GoMintServer server;
    private final Logger logger = LoggerFactory.getLogger( NetworkManager.class );

    // Connections which were closed and should be removed during next tick:
    private final LongSet closedConnections = HashLongSets.newMutableSet();
    private ServerSocket socket;
    private LongObjMap<PlayerConnection> playersByGuid = HashLongObjMaps.newMutableMap();

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
        if ( this.socket != null ) {
            throw new IllegalStateException( "Cannot re-initialize network manager" );
        }

        this.socket = new ServerSocket( maxConnections < 0 ? 20 : maxConnections );
        this.socket.setEventLoopFactory( this.server.getThreadFactory() );
        this.socket.setEventHandler( new SocketEventHandler() {
            @Override
            public void onSocketEvent( Socket socket, SocketEvent socketEvent ) {
                NetworkManager.this.handleSocketEvent( socketEvent );
            }
        } );
        this.socket.bind( host, port );

        this.dump = false;
        this.dumpDirectory = null;
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
            this.playersByGuid.put( connection.getConnection().getGuid(), connection );
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
        }
    }

    /**
     * Broadcasts the given packet to all players. Yields the same effect as invoking
     * {@link #broadcast(PacketReliability, int, Packet)} with {@link PacketReliability#RELIABLE} and
     * orderingChannel set to zero.
     *
     * @param packet The packet to broadcast
     */
    public void broadcast( Packet packet ) {
        this.broadcast( PacketReliability.RELIABLE, 0, packet );
    }

    /**
     * Broadcasts the given packet to all players.
     *
     * @param reliability     Raknet Reliability with which this packet should be send
     * @param orderingChannel In which channel should this packet be send
     * @param packet          The packet to broadcast
     */
    public void broadcast( PacketReliability reliability, int orderingChannel, Packet packet ) {
        LongObjCursor<PlayerConnection> cursor = this.playersByGuid.cursor();
        while ( cursor.moveNext() ) {
            cursor.value().send( reliability, orderingChannel, packet );
        }
    }

    // ======================================= INTERNALS ======================================= //

    /**
     * Used by player connections in order to log warnings and errors.
     *
     * @return The network manager's own logger
     */
    Logger getLogger() {
        return this.logger;
    }

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
    public void notifyUnknownPacket( byte packetId, PacketBuffer buffer ) {
        if ( this.dump ) {
            this.logger.info( "Received unknown packet 0x" + Integer.toHexString( ( (int) packetId ) & 0xFF ) );
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
                this.handleConnectionClosed( event.getConnection() );
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
                new PingEvent( this.server.getMotd(), this.server.getAmountOfPlayers(), this.server.getServerConfig().getMaxPlayers() )
        );

        event.getPingPongInfo().setMotd( "MCPE;" + pingEvent.getMotd() + ";" + Protocol.MINECRAFT_PE_PROTOCOL_VERSION + ";" + Protocol.MINECRAFT_PE_NETWORK_VERSION + ";" + pingEvent.getOnlinePlayers() + ";" + pingEvent.getMaxPlayers() );
    }

    /**
     * Handles a new incoming connection.
     *
     * @param connection The new incoming connection
     */
    private void handleNewConnection( Connection connection ) {
        this.incomingConnections.add( new PlayerConnection( this, connection, PlayerConnectionState.HANDSHAKE ) );
    }

    /**
     * Handles a connection that just got closed.
     *
     * @param connection The connection that closed
     */
    private void handleConnectionClosed( Connection connection ) {
        synchronized ( this.closedConnections ) {
            this.closedConnections.add( connection.getGuid() );
        }
    }

    private void dumpPacket( byte packetId, PacketBuffer buffer ) {
        this.logger.info( "Dumping packet " + Integer.toHexString( ( (int) packetId ) & 0xFF ) );

        String filename = Integer.toHexString( ( (int) packetId ) & 0xFF );
        while ( filename.length() < 2 ) {
            filename = "0" + filename;
        }
        filename += "_" + System.currentTimeMillis();
        filename += ".dump";

        File dumpFile = new File( this.dumpDirectory, filename );

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
            this.logger.error( "Failed to dump packet " + filename );
        }
    }

    // ======================================= PACKET HANDLERS ======================================= //

}
