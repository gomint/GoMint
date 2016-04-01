/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.jraknet.Connection;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import io.gomint.server.GoMintServer;
import io.gomint.server.network.packet.Packet;
import net.openhft.koloboke.collect.LongCursor;
import net.openhft.koloboke.collect.ObjCursor;
import net.openhft.koloboke.collect.map.LongObjMap;
import net.openhft.koloboke.collect.map.hash.HashLongObjMaps;
import net.openhft.koloboke.collect.set.LongSet;
import net.openhft.koloboke.collect.set.hash.HashLongSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public class NetworkManager {

    private final GoMintServer gomint;
    private final Logger logger = LoggerFactory.getLogger( NetworkManager.class );

    private ServerSocket socket;

    private LongObjMap<PlayerConnection> playersByGuid = HashLongObjMaps.newMutableMap();

    // Incoming connections to be added to the player map during next tick:
    private Queue<PlayerConnection> incomingConnections = new ConcurrentLinkedQueue<>();
    // Connections which were closed and should be removed during next tick:
    private LongSet closedConnections = HashLongSets.newMutableSet();

    // Packet Dumping
    private boolean dump;
    private File dumpDirectory;

    /**
     * Init a new NetworkManager for accepting new connections and read incoming data
     *
     * @param gomint server instance which should be used
     */
    public NetworkManager( GoMintServer gomint ) {
        this.gomint = gomint;
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

        this.socket = new ServerSocket( maxConnections );
        this.socket.setEventLoopFactory( this.gomint.getThreadFactory() );
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
     */
    public void tick() {
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
                    PlayerConnection connection = this.playersByGuid.get( guid );
                    if ( connection != null ) {
                        connection.close();
                        this.playersByGuid.remove( guid );
                    }
                }

                this.closedConnections.clear();
            }
        }

        // Tick all player connections in order to receive all incoming packets:
        ObjCursor<PlayerConnection> cursor = this.playersByGuid.values().cursor();
        while ( cursor.moveNext() ) {
            PlayerConnection connection = cursor.elem();
            connection.tick();
        }
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
     * Sets the MOTD to be sent inside server pongs.
     *
     * @param motd The motd to be sent inside server pongs
     */
    public void setMotd( String motd ) {
        this.socket.setMotd( motd );
    }

    /**
     * Gets the MOTD to be sent inside server pongs.
     *
     * @return The motd to be sent inside server pongs
     */
    public String getMotd() {
        return this.socket.getMotd();
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
    GoMintServer getServer() {
        return this.gomint;
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

    public void broadcastPacket( Packet packet ) {
        if( packet != null ){
            for ( PlayerConnection playerConnection : this.playersByGuid.values() ) {
                if( playerConnection.getState() == PlayerConnectionState.PLAYING ) {
                    playerConnection.send( packet );
                }
            }
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
                this.handleNewConnection( event.getConnection() );
                break;

            case CONNECTION_CLOSED:
            case CONNECTION_DISCONNECTED:
                this.handleConnectionClosed( event.getConnection() );
                break;
        }
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
        this.logger.info( "Dumping packet 0x" + Integer.toHexString( ( (int) packetId ) & 0xFF ) );

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

}
