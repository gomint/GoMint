package io.gomint.jraknet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import static io.gomint.jraknet.RakNetConstraints.UNCONNECTED_PING;
import static io.gomint.jraknet.RakNetConstraints.UNCONNECTED_PONG;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ClientSocket extends Socket {

    // Logging
    private final Logger logger;

    private ClientConnection connection;
    boolean mojangModificationEnabled;

    /**
     * Constructs a new client socket ready for use.
     */
    public ClientSocket() {
        this.logger = LoggerFactory.getLogger( ClientSocket.class );
        this.connection = null;
        this.generateGuid();
    }

    /**
     * Constructs a new client socket ready for use that will use the given logger for all
     * of its logging purposes.
     */
    public ClientSocket( Logger logger ) {
        this.logger = logger;
        this.connection = null;
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
     * Initializes this socket and binds its internal udp socket to a free port.
     * If the socket is already initialized any invocation of this method will
     * result in an IllegalStateException.
     *
     * @throws SocketException Thrown in case the socket could not be initialized
     */
    public void initialize() throws SocketException {
        if ( this.isInitialized() ) {
            throw new IllegalStateException( "Cannot re-initialized ClientSocket" );
        }

        this.udpSocket = new Bootstrap();
        this.udpSocket.group( EventLoops.LOOP_GROUP );
        this.udpSocket.channel( Epoll.isAvailable() ? EpollDatagramChannel.class : NioDatagramChannel.class );
        this.udpSocket.handler( new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
                io.netty.channel.socket.DatagramPacket packet = (io.netty.channel.socket.DatagramPacket) msg;
                PacketBuffer content = new PacketBuffer( packet.content() );
                InetSocketAddress sender = packet.sender();

                if ( !receiveDatagram( sender, content ) ) {
                    // Push datagram to update queue:
                    handleDatagram( sender, content, System.currentTimeMillis() );
                }
            }
        } );

        try {
            this.channel = this.udpSocket.bind( ThreadLocalRandom.current().nextInt( 45000, 65000 ) ).sync().channel();
        } catch ( InterruptedException e ) {
            SocketException exception = new SocketException( "Could not bind to socket" );
            exception.initCause( e );
            throw exception;
        }
    }

    /**
     * Sends one unconnected ping packet to the specified target system. Note though that the
     * ping is not guaranteed to ever reach the target system nor is the pong response ever
     * guaranteed to reach this client socket. If this socket should ever receive a pong response
     * from the target system it will generate an {@link io.gomint.jraknet.SocketEvent.Type#UNCONNECTED_PONG} event.
     * The socket must have been initialized before this method is invoked otherwise an IllegalStateException
     * will be thrown.
     *
     * @param hostname The hostname of the target system
     * @param port     The port of the target system
     */
    public void ping( String hostname, int port ) {
        this.ping( new InetSocketAddress( hostname, port ) );
    }

    /**
     * Sends one unconnected ping packet to the specified target system. Note though that the
     * ping is not guaranteed to ever reach the target system nor is the pong response ever
     * guaranteed to reach this client socket. If this socket should ever receive a pong response
     * from the target system it will generate an {@link io.gomint.jraknet.SocketEvent.Type#UNCONNECTED_PONG} event.
     * The socket must have been initialized before this method is invoked otherwise an IllegalStateException
     * will be thrown.
     *
     * @param address The address of the target system
     */
    public void ping( InetSocketAddress address ) {
        if ( !this.isInitialized() ) {
            throw new IllegalStateException( "Cannot ping before initialization" );
        }

        this.sendUnconnectedPing( address );
    }

    /**
     * Attempts to connect to the specified target system. This operation will perform asynchronously and
     * generate appropriate socket events once it fails or completes. The socket must have been initialized
     * before this method is invoked otherwise an IllegalStateException will be thrown. If the socket
     * is already trying to connect to a remote system another invocation of this method will also result in
     * an IllegalStateException.
     *
     * @param hostname The hostname of the target system
     * @param port     The port of the target system
     */
    public void connect( String hostname, int port ) {
        this.connect( new InetSocketAddress( hostname, port ) );
    }

    /**
     * Attempts to connect to the specified target system. This operation will perform asynchronously and
     * generate appropriate socket events once it fails or completes. The socket must have been initialized
     * before this method is invoked otherwise an IllegalStateException will be thrown. If the socket
     * is already trying to connect to a remote system another invocation of this method will also result in
     * an IllegalStateException.
     *
     * @param address The address of the target system
     */
    public void connect( InetSocketAddress address ) {
        if ( !this.isInitialized() ) {
            throw new IllegalStateException( "Cannot ping before initialization" );
        }

        if ( this.connection != null ) {
            throw new IllegalStateException( "Cannot connect whilst an open connection exists" );
        }

        // Connection will start to send pre-connection requests automatically:
        this.connection = new ClientConnection( this, address, ConnectionState.INITIALIZING );
    }

    /**
     * Gets the client socket's connection to the remote peer it connected to if a previous
     * connection attempt has succeeded.
     *
     * @return The socket's connection to its remote peer
     */
    public Connection getConnection() {
        return this.connection;
    }

    // ================================ IMPLEMENTATION HOOKS ================================ //

    /**
     * Gets a logger to be used for logging errors and warnings.
     *
     * @return The logger to be used for logging errors and warnings
     */
    @Override
    public Logger getImplementationLogger() {
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
        // Check if this might be an unconnected pong:
        byte packetId = datagram.getBuffer()[0];
        if ( packetId == UNCONNECTED_PONG ) {
            this.handleUnconnectedPong( sender, datagram );
            return true;
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
        if ( this.connection != null ) {
            this.connection.handleDatagram( sender, datagram, time );
        }
    }

    // ================================ INTERNALS ================================ //

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
    void send( InetSocketAddress recipient, byte[] buffer, int offset, int length ) {
        if ( this.channel != null ) {
            this.flush( new Flusher.FlushItem( this.channel, new DatagramPacket( Unpooled.wrappedBuffer( buffer, offset, length ), recipient ) ) );
        }
    }

    /**
     * Invoked by a client connection whenever it got disconnected by its remote end.
     *
     * @param connection The connection that got disconnected
     */
    void propagateConnectionClosed( ClientConnection connection ) {
        this.connection = null;
        this.propagateEvent( new SocketEvent( SocketEvent.Type.CONNECTION_CLOSED, connection ) );
    }

    /**
     * Invoked by a client connection whenever this side of the network is disconnecting from the remote peer.
     *
     * @param connection The connection that disconnected
     */
    void propagateConnectionDisconnected( ClientConnection connection ) {
        this.connection = null;
        this.propagateEvent( new SocketEvent( SocketEvent.Type.CONNECTION_DISCONNECTED, connection ) );
    }

    /**
     * Invoked by a client connection whenever it could successfully establish a connection to a remote end
     *
     * @param connection The connection that successfully established a connection
     */
    void propagateConnectionRequestSucceded( ClientConnection connection ) {
        this.propagateEvent( new SocketEvent( SocketEvent.Type.CONNECTION_ATTEMPT_SUCCEEDED, connection ) );
    }

    /**
     * Invoked by a client connection whenever its connection attempt failed.
     *
     * @param reason The reason why the connection attempt failed
     */
    void propagateConnectionAttemptFailed( String reason ) {
        this.connection = null;
        this.propagateEvent( new SocketEvent( SocketEvent.Type.CONNECTION_ATTEMPT_FAILED, reason ) );
    }

    /**
     * Sends an unconnected ping packet to the specified recipient.
     *
     * @param recipient The address to send the ping to
     */
    private void sendUnconnectedPing( InetSocketAddress recipient ) {
        PacketBuffer buffer = new PacketBuffer( 9 );
        buffer.writeByte( UNCONNECTED_PING );
        buffer.writeLong( System.currentTimeMillis() );
        buffer.writeBytes( RakNetConstraints.OFFLINE_MESSAGE_DATA_ID );
        buffer.writeLong( this.getGuid() );

        this.send( recipient, buffer );
    }

    /**
     * Handles unconnected pong datagrams and generates the appropriate socket events.
     *
     * @param datagram The datagram containing the unconnected pong packet
     */
    private void handleUnconnectedPong( InetSocketAddress sender, PacketBuffer datagram ) {
        datagram.skip( 1 );                   // Packet ID

        long pingTime = datagram.readLong();
        long serverGuid = datagram.readLong();
        datagram.readOfflineMessageDataId();

        String motd = null;
        if ( datagram.getRemaining() > 0 ) {
            int motdLength = datagram.readUShort();
            byte[] motdBytes = new byte[motdLength];
            datagram.readBytes( motdBytes );

            motd = new String( motdBytes, StandardCharsets.US_ASCII );
        }

        SocketEvent.PingPongInfo info = new SocketEvent.PingPongInfo( sender,
                pingTime,
                System.currentTimeMillis(),
                serverGuid,
                motd );
        this.propagateEvent( new SocketEvent( SocketEvent.Type.UNCONNECTED_PONG, info ) );
    }

    public void removeConnection() {
        this.connection = null;
    }

}