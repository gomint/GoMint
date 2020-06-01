package io.gomint.jraknet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static io.gomint.jraknet.RakNetConstraints.OPEN_CONNECTION_REQUEST_1;
import static io.gomint.jraknet.RakNetConstraints.UNCONNECTED_PING;
import static io.gomint.jraknet.RakNetConstraints.UNCONNECTED_PING_OPEN_CONNECTION;
import static io.gomint.jraknet.RakNetConstraints.UNCONNECTED_PONG;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 2.0
 */
public class ServerSocket extends Socket {

    // Logging
    private final Logger logger;

    // Address the socket was bound to if already bound:
    private InetSocketAddress bindAddress;

    // RakNet data:
    private final int maxConnections;
    private Map<SocketAddress, ServerConnection> connectionsByAddress;
    private Map<Long, ServerConnection> connectionsByGuid;
    private Set<ServerConnection> activeConnections;

    // Modification data:
    boolean mojangModificationEnabled;

    /**
     * Constructs a new server socket which will allow for maxConnections concurrently playing
     * players at max.
     *
     * @param maxConnections The maximum connections allowed on this socket
     */
    public ServerSocket( int maxConnections ) {
        this.logger = LoggerFactory.getLogger( ServerSocket.class );
        this.maxConnections = maxConnections;
        this.activeConnections = new HashSet<>();
        this.generateGuid();
    }

    /**
     * Constructs a new server socket which will allow for maxConnections concurrently playing
     * players at max. Also it will use the specified logger for all logging it performs.
     *
     * @param maxConnections The maximum connections allowed on this socket
     */
    public ServerSocket( Logger logger, int maxConnections ) {
        this.logger = logger;
        this.maxConnections = maxConnections;
        this.activeConnections = new HashSet<>();
        this.generateGuid();
    }

    // ================================ PUBLIC API ================================ //

    /**
     * Enable mojang modifications to this is compatible with MC:PE
     *
     * @param enable The setting of the modification. True if jRaknet should handle MC:PE mods, false if not
     */
    public void setMojangModificationEnabled( boolean enable ) {
        this.mojangModificationEnabled = enable;
    }

    /**
     * Binds the server socket to the specified port. This operation initializes this socket.
     *
     * @param host The hostname to bind to (either an IP or a FQDN)
     * @param port The port to bind to
     * @throws SocketException Thrown if the socket cannot be bound
     */
    public void bind( String host, int port ) throws SocketException {
        this.bind( new InetSocketAddress( host, port ) );
    }

    /**
     * Binds the server socket to the specified address. This operation initializes this socket.
     *
     * @param address The address to bind the port to
     * @throws SocketException Thrown if the socket cannot be bound
     */
    public void bind( InetSocketAddress address ) throws SocketException {
        if ( this.bindAddress != null ) {
            throw new SocketException( "ServerSocket is already bound" );
        }

        // Automatically binds socket to address (no further #bind() call required)
        this.udpSocket = new Bootstrap();
        this.udpSocket.group( EventLoops.LOOP_GROUP );
        this.udpSocket.channel( Epoll.isAvailable() ? EpollDatagramChannel.class : NioDatagramChannel.class );
        this.udpSocket.handler( new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
                DatagramPacket packet = (DatagramPacket) msg;
                PacketBuffer content = new PacketBuffer( packet.content() );
                InetSocketAddress sender = packet.sender();

                getImplementationLogger().trace( "IN> {}", content );

                if ( !receiveDatagram( sender, content ) ) {
                    // Push datagram to update queue:
                    handleDatagram( sender, content, System.currentTimeMillis() );
                }
            }
        } );

        try {
            this.channel = this.udpSocket.bind( address ).sync().channel();
        } catch ( InterruptedException e ) {
            SocketException exception = new SocketException( "Could not bind to socket" );
            exception.initCause( e );
            throw exception;
        }

        this.bindAddress = address;

        this.connectionsByAddress = new ConcurrentHashMap<>( this.maxConnections );
        this.connectionsByGuid = new ConcurrentHashMap<>( this.maxConnections );
    }

    /**
     * Gets the address the socket is bound to if it is already bound.
     *
     * @return The address the socket is bound to or null if it is not bound yet
     */
    public InetSocketAddress getBindAddress() {
        return this.bindAddress;
    }

    /**
     * Closes the socket and cleans up all internal resources. If any other method is invoked on this
     * socket after a call to this method was made the behaviour of the socket is undefined.
     */
    @Override
    public void close() {
        super.close();

        // Disconnect all connections
        for ( ServerConnection connection : this.activeConnections ) {
            connection.disconnect( "Server shutdown" );
        }

        this.bindAddress = null;
    }

    // ================================ IMPLEMENTATION HOOKS ================================ //

    /**
     * Gets a logger to be used for logging errors and warnings.
     *
     * @return The logger to be used for logging errors and warnings
     */
    @Override
    protected Logger getImplementationLogger() {
        return this.logger;
    }

    /**
     * Invoked right after a datagram was received. This method may perform very rudimentary
     * datagram handling if necessary.
     *
     * @param datagram The datagram that was just received
     * @return Whether or not the datagram was handled by this method already and should be processed no further
     */
    @Override
    protected boolean receiveDatagram( InetSocketAddress sender, PacketBuffer datagram ) {
        // Handle unconnected pings:
        byte packetId = datagram.getBuffer()[0];

        if ( packetId == UNCONNECTED_PING ) {
            this.handleUnconnectedPing( sender, datagram );
            return true;
        } else if ( packetId == UNCONNECTED_PING_OPEN_CONNECTION ) {
            if ( this.connectionsByGuid.size() < this.maxConnections ) {
                this.handleUnconnectedPing( sender, datagram );
            }

            return true; // We always handle this but we may not send a response
        }

        return false;
    }

    /**
     * Handles the given datagram. This will be invoked on the socket's update thread and should hand
     * this datagram to the connection it belongs to in order to deserialize it appropriately.
     *
     * @param datagram The datagram to be handled
     * @param time     The current system time
     */
    @Override
    protected void handleDatagram( InetSocketAddress sender, PacketBuffer datagram, long time ) {
        ServerConnection connection = this.getConnection( sender, datagram );
        if ( connection != null ) {
            // #getConnection() may return null if there is currently no connection
            // associated with the socket address the datagram came from and the
            // datagram itself does not contain the first packet of the connection
            // attempt chain:
            connection.handleDatagram( sender, datagram, time );
        }
    }

    // ================================ INTERNALS ================================ //

    /**
     * Checks whether or not another connection with this address's system address or
     * client GUID already exists.
     *
     * @param connection The connection to test
     * @return Whether or not the connection already exists
     */
    boolean testAddressAndGuid( ServerConnection connection ) {
        return !this.connectionsByGuid.containsKey( connection.getGuid() );
    }

    /**
     * Checks whether or not another new incoming is allowed (max connections).
     *
     * @return Whether or not another new incoming connection should be allowed
     */
    boolean allowIncomingConnection() {
        return ( this.activeConnections.size() < this.maxConnections );
    }

    /**
     * Sends the given data to the specified recipient immediately, i.e. without caching nor any form of
     * transmission control (reliability, resending, etc.)
     *
     * @param recipient The recipient of the data
     * @param buffer    The buffer to transmit
     */
    void send( InetSocketAddress recipient, PacketBuffer buffer ) {
        this.send( recipient, buffer.getBuffer(), buffer.getBufferOffset(), buffer.getPosition() - buffer.getBufferOffset() );
    }

    /**
     * Sends the given data to the specified recipient immediately, i.e. without caching nor any form of
     * transmission control (reliability, resending, etc.)
     *
     * @param recipient The recipient of the data
     * @param buffer    The buffer holding the data to send
     * @param offset    The offset into the buffer
     * @param length    The length of the data chunk to send
     */
    private void send( InetSocketAddress recipient, byte[] buffer, int offset, int length ) {
        if ( this.channel != null ) {
            this.flush( new Flusher.FlushItem( this.channel, new DatagramPacket( Unpooled.wrappedBuffer( buffer, offset, length ), recipient ) ) );
        }
    }

    /**
     * Notifies the server socket that the specified connection has received the final connection request
     * before it may switch into the connected state thus resulting in a fully established connection.
     * At this point the connection would need to be considered a current connection, thus if this method
     * returns true the server socket will have prepared everything in order to consider this connection
     * a fully valid current connection.
     *
     * @param connection The connection that received the final connection request
     * @return Whether or not the connection request should be accepted.
     */
    boolean notifyConnectionRequest( ServerConnection connection ) {
        if ( this.activeConnections.size() >= this.maxConnections ) {
            return false;
        }

        this.activeConnections.add( connection );
        return true;
    }

    /**
     * Invoked by a server connection that was just fully established. This is the point at which connection
     * attempts may be announced to the end user as the underlying connection does now support reliable messages
     * and is fully initialized.
     *
     * @param connection The connection that is fully established now
     */
    void propagateFullyConnectedConnection( ServerConnection connection ) {
        this.logger.info( "Fully established new incoming connection: " + connection.getAddress() );
        this.propagateEvent( new SocketEvent( SocketEvent.Type.NEW_INCOMING_CONNECTION, connection ) );
    }

    /**
     * Invoked by a server connection whenever it got disconnected by its remote end.
     *
     * @param connection The connection that got disconnected
     */
    void propagateConnectionClosed( ServerConnection connection ) {
        // Got to handle this disconnect:
        this.removeActiveConnection( connection );

        this.propagateEvent( new SocketEvent( SocketEvent.Type.CONNECTION_CLOSED, connection ) );
    }

    /**
     * Invoked by a server connection whenever this side of the network is disconnecting from the remote peer.
     *
     * @param connection The connection that disconnected
     */
    void propagateConnectionDisconnected( ServerConnection connection ) {
        // Got to handle this disconnect:
        this.removeActiveConnection( connection );

        this.propagateEvent( new SocketEvent( SocketEvent.Type.CONNECTION_DISCONNECTED, connection ) );
    }

    /**
     * Handles and responds to an unconnected ping sent from another system.
     *
     * @param datagram The datagram containing the ping request
     */
    private void handleUnconnectedPing( InetSocketAddress sender, PacketBuffer datagram ) {
        // Indeed, rather ugly but avoids the relatively unnecessary cost of
        // constructing yet another packet buffer instance:
        datagram.skip( 1 );
        long sendPingTime = datagram.readLong();

        // Let the SocketEventHandler decide what to send
        SocketEvent.PingPongInfo info = new SocketEvent.PingPongInfo( sender, sendPingTime, sendPingTime, -1, "" );
        this.propagateEvent( new SocketEvent( SocketEvent.Type.UNCONNECTED_PING, info ) );

        byte[] motdBytes = info.getMotd().getBytes( StandardCharsets.UTF_8 );
        PacketBuffer packet = new PacketBuffer( 35 + motdBytes.length );

        packet.writeByte( UNCONNECTED_PONG );
        packet.writeLong( sendPingTime );
        packet.writeLong( this.getGuid() );
        packet.writeOfflineMessageDataId();

        packet.writeUShort( motdBytes.length );
        packet.writeBytes( motdBytes );

        this.send( sender, packet );
    }

    /**
     * Removes a currently active connection from the server socket and cleans up any resources allocated
     * for it.
     *
     * @param connection The connection to remove
     */
    private void removeActiveConnection( @SuppressWarnings( "unused" ) ServerConnection connection ) {
        this.activeConnections.remove( connection );
    }

    /**
     * Gets or creates a connection given its socket address. Must only be invoked in update thread in order
     * to ensure thread safety. May return null if there is no connection associated with the sender of the
     * given datagram and the received datagram does not contain a connection attempt packet
     *
     * @param datagram The datagram buffer holding the actual datagram data. USed to access socket address of sender and packet ID
     * @return The connection of the given address or null (see above)
     */
    private ServerConnection getConnection( InetSocketAddress sender, PacketBuffer datagram ) {
        ServerConnection connection = this.connectionsByAddress.get( sender );
        if ( connection == null ) {
            // Only construct a new server connection if this datagram contains
            // a valid OPEN_CONNECTION_REQUEST_1 packet as this might be a discarded
            // or invalid connection receive otherwise:
            byte packetId = datagram.getBuffer()[0];
            if ( packetId == OPEN_CONNECTION_REQUEST_1 ) {
                this.getImplementationLogger().trace( "Generated new server connection for {}", sender );

                connection = new ServerConnection( this, sender, ConnectionState.UNCONNECTED );
                this.connectionsByAddress.put( sender, connection );
            }
        }

        return connection;
    }

    void removeConnection( ServerConnection connection ) {
        this.connectionsByAddress.remove( connection.getAddress() );

        if ( connection.hasGuid() ) {
            this.connectionsByGuid.remove( connection.getGuid() );
        }

        this.activeConnections.remove( connection );
    }

    void addGuidConnection( ServerConnection connection ) {
        this.connectionsByGuid.put( connection.getGuid(), connection );
    }

}
