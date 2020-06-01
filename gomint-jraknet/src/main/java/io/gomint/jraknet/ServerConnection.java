package io.gomint.jraknet;

import org.slf4j.Logger;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicLong;

import static io.gomint.jraknet.RakNetConstraints.ALREADY_CONNECTED;
import static io.gomint.jraknet.RakNetConstraints.CONNECTION_REQUEST;
import static io.gomint.jraknet.RakNetConstraints.CONNECTION_REQUEST_ACCEPTED;
import static io.gomint.jraknet.RakNetConstraints.CONNECTION_TIMEOUT_MILLIS;
import static io.gomint.jraknet.RakNetConstraints.INCOMPATIBLE_PROTOCOL_VERSION;
import static io.gomint.jraknet.RakNetConstraints.MAXIMUM_MTU_SIZE;
import static io.gomint.jraknet.RakNetConstraints.MAX_LOCAL_IPS;
import static io.gomint.jraknet.RakNetConstraints.NEW_INCOMING_CONNECTION;
import static io.gomint.jraknet.RakNetConstraints.NO_FREE_INCOMING_CONNECTIONS;
import static io.gomint.jraknet.RakNetConstraints.OPEN_CONNECTION_REPLY_1;
import static io.gomint.jraknet.RakNetConstraints.OPEN_CONNECTION_REPLY_2;
import static io.gomint.jraknet.RakNetConstraints.OPEN_CONNECTION_REQUEST_1;
import static io.gomint.jraknet.RakNetConstraints.OPEN_CONNECTION_REQUEST_2;
import static io.gomint.jraknet.RakNetConstraints.RAKNET_PROTOCOL_VERSION;
import static io.gomint.jraknet.RakNetConstraints.RAKNET_PROTOCOL_VERSION_MOJANG;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class ServerConnection extends Connection {

    // References
    private final ServerSocket server;

    // Lost connection
    private long lastConnectionLostCheck;

    // Traffic counters
    private long lastTrafficStats;
    private AtomicLong bytesSend = new AtomicLong( 0 );
    private AtomicLong bytesReceived = new AtomicLong( 0 );

    ServerConnection( ServerSocket server, InetSocketAddress address, ConnectionState initialState ) {
        super( address, initialState );
        this.server = server;
    }

    // ================================ CONNECTION ================================ //

    /**
     * Sends raw data through an implementation-specific datagram socket. The data will already be encoded
     * properly and is only required to be sent directly.
     *
     * @param recipient The recipient of the data
     * @param buffer    The buffer containing the data to be sent
     */
    @Override
    protected void sendRaw( InetSocketAddress recipient, PacketBuffer buffer ) {
        this.bytesSend.addAndGet( buffer.getPosition() - (long) buffer.getBufferOffset() );
        this.server.send( recipient, buffer );
    }

    /**
     * Gets a logger to be used for logging errors and warnings.
     *
     * @return The logger to be used for logging errors and warnings
     */
    @Override
    protected Logger getImplementationLogger() {
        if ( this.server == null ) {
            return null;
        }

        return this.server.getImplementationLogger();
    }

    @Override
    void handleDatagram( InetSocketAddress sender, PacketBuffer datagram, long time ) {
        this.bytesReceived.addAndGet( datagram.getRemaining() );
        super.handleDatagram( sender, datagram, time );
    }

    /**
     * Implementation hook.
     *
     * @param datagram The datagram to be handled
     * @param time     The current system time
     * @return Whether or not the datagram was handled already and should be processed no further
     */
    @Override
    protected boolean handleDatagram0( InetSocketAddress sender, PacketBuffer datagram, long time ) {
        // Handle special internal packets:
        byte packetId = datagram.getBuffer()[0];
        switch ( packetId ) {
            case OPEN_CONNECTION_REQUEST_1:
                this.handlePreConnectionRequest1( sender, datagram );
                return true;
            case OPEN_CONNECTION_REQUEST_2:
                this.handlePreConnectionRequest2( datagram );
                return true;
            default:
                return false;
        }
    }

    /**
     * Implementation hook.
     *
     * @param packet The packet to be handled
     * @return Whether or not the packet was handled already and should be processed no further
     */
    @Override
    protected boolean handlePacket0( EncapsulatedPacket packet ) {
        // Handle special internal packets:
        byte packetId = packet.getPacketData()[0];
        switch ( packetId ) {
            case CONNECTION_REQUEST:
                this.handleConnectionRequest( packet );
                return true;
            case NEW_INCOMING_CONNECTION:
                this.handleNewIncomingConnection();
                return true;
            default:
                return false;
        }
    }

    /**
     * Invoked whenever the connection was closed for some reason. This event should be propagated to the
     * socket the specific implementation was created by.
     */
    @Override
    protected void propagateConnectionClosed() {
        this.server.propagateConnectionClosed( this );
    }

    /**
     * Invoked whenever the connection disconnected for some reason. This event should be propagated to the
     * socket the specific implementation was created by.
     */
    @Override
    protected void propagateConnectionDisconnected() {
        this.server.propagateConnectionDisconnected( this );
    }

    /**
     * Invoked whenever the connection switched to an entirely connected state. This event should be propagated to the
     * socket the specific implementation was created by.
     */
    @Override
    protected void propagateFullyConnected() {
        this.server.propagateFullyConnectedConnection( this );
    }

    // ================================ PACKET HANDLERS ================================ //

    @Override
    boolean update( long time ) {
        // Timeout
        if ( this.getLastReceivedPacketTime() + CONNECTION_TIMEOUT_MILLIS < time ) {
            this.notifyTimeout();
            return false;
        }

        return super.update( time );
    }

    @Override
    void notifyRemoval() {
        this.server.removeConnection( this );
        super.notifyRemoval();
    }

    private void handlePreConnectionRequest1( InetSocketAddress sender, PacketBuffer datagram ) {
        if ( !this.server.testAddressAndGuid( this ) ) {
            this.sendAlreadyConnected();
            return;
        }

        this.setState( ConnectionState.INITIALIZING );
        datagram.skip( 1 );
        datagram.readOfflineMessageDataId();

        byte remoteProtocol = datagram.readByte();

        // Check for correct protocol:
        if ( ( !server.mojangModificationEnabled && remoteProtocol != RAKNET_PROTOCOL_VERSION ) ||
                ( server.mojangModificationEnabled && ( remoteProtocol != RAKNET_PROTOCOL_VERSION_MOJANG ) ) ) {
            this.sendIncompatibleProtocolVersion();
            this.setState( ConnectionState.UNCONNECTED );
            return;
        }

        this.sendConnectionReply1( sender, datagram.getRemaining() + 18 );
    }

    private void handlePreConnectionRequest2( PacketBuffer datagram ) {
        datagram.skip( 1 );                                                                       // Packet ID
        datagram.readOfflineMessageDataId();                                                      // Offline Message Data ID
        datagram.readAddress();                                                                   // Address the client bound to
        this.setMtuSize( datagram.readUShort() );                                                 // MTU
        this.setGuid( datagram.readLong() );                                                      // Client GUID

        if ( !this.server.testAddressAndGuid( this ) ) {
            this.sendAlreadyConnected();
            return;
        }

        if ( !this.server.allowIncomingConnection() ) {
            this.sendNoFreeIncomingConnections();
            return;
        }

        // Do this before sending the response:
        this.initializeStructures();
        this.server.addGuidConnection( this );

        this.sendConnectionReply2();
        this.setState( ConnectionState.RELIABLE );
    }

    private void handleConnectionRequest( EncapsulatedPacket packet ) {
        this.setState( ConnectionState.CONNECTING );

        PacketBuffer buffer = new PacketBuffer( packet.getPacketData(), 0 );
        buffer.skip( 1 );                               // Packet ID
        if ( this.getGuid() != buffer.readLong() ) {    // Client GUID
            this.setState( ConnectionState.UNCONNECTED );
            this.sendConnectionRequestFailed();
            return;
        }

        long time = buffer.readLong();    // Current client time
        boolean securityEnabled = buffer.readBoolean(); // Whether or not a password is appended to the packet's usual data

        if ( securityEnabled ) {
            // We do not support security!
            this.getImplementationLogger().warn( "Connection {} requested raknet security, we don't support that", this.getAddress().getAddress() );
            this.sendConnectionRequestFailed();
            return;
        }

        this.sendConnectionRequestAccepted( time );
    }

    private void handleNewIncomingConnection() {
        if ( this.getState() != ConnectionState.CONNECTING ) {
            // Connection is not in a valid state to handle this packet:
            return;
        }

        if ( !this.server.notifyConnectionRequest( this ) ) {
            // This connection is hereby cancelled immediately!
            this.setState( ConnectionState.UNCONNECTED );
            this.sendNoFreeIncomingConnections();
            return;
        }

        // Connection is now ready to rumble!
        this.setState( ConnectionState.CONNECTED );
    }

    // ================================ PACKET SENDERS ================================ //

    private void sendIncompatibleProtocolVersion() {
        PacketBuffer buffer = new PacketBuffer( 26 );
        buffer.writeByte( INCOMPATIBLE_PROTOCOL_VERSION );
        buffer.writeByte( server.mojangModificationEnabled ? RAKNET_PROTOCOL_VERSION_MOJANG : RAKNET_PROTOCOL_VERSION );
        buffer.writeOfflineMessageDataId();
        buffer.writeLong( this.server.getGuid() );
        this.sendRaw( this.getAddress(), buffer );
    }

    private void sendConnectionReply1( InetSocketAddress sender, int length ) {
        // The request packet will be as large as possible in order to determine the MTU size:
        int mtuSize = length;
        if ( mtuSize > MAXIMUM_MTU_SIZE ) {
            mtuSize = MAXIMUM_MTU_SIZE;
        }

        PacketBuffer buffer = new PacketBuffer( 28 );
        buffer.writeByte( OPEN_CONNECTION_REPLY_1 );
        buffer.writeOfflineMessageDataId();
        buffer.writeLong( this.server.getGuid() );
        buffer.writeByte( (byte) 0x00 ); // We are not using LIBCAT Security
        buffer.writeUShort( mtuSize );

        this.sendRaw( sender, buffer );
    }

    private void sendAlreadyConnected() {
        this.sendConnectionRequestAbort( ALREADY_CONNECTED );
    }

    private void sendNoFreeIncomingConnections() {
        this.sendConnectionRequestAbort( NO_FREE_INCOMING_CONNECTIONS );
    }

    private void sendConnectionRequestAbort( byte id ) {
        PacketBuffer buffer = new PacketBuffer( 21 );
        buffer.writeByte( id );
        buffer.writeOfflineMessageDataId();
        buffer.writeLong( this.server.getGuid() );

        this.sendRaw( this.getAddress(), buffer );
    }

    private void sendConnectionReply2() {
        PacketBuffer buffer = new PacketBuffer( 31 );
        buffer.writeByte( OPEN_CONNECTION_REPLY_2 );    // Packet ID
        buffer.writeOfflineMessageDataId();             // Offline Message Data ID
        buffer.writeLong( this.server.getGuid() );      // Server GUID
        buffer.writeAddress( this.getAddress() );
        buffer.writeUShort( this.getMtuSize() );        // MTU
        buffer.writeBoolean( false );                   // Not using LIBCAT Security

        this.sendRaw( this.getAddress(), buffer );
    }

    private void sendConnectionRequestAccepted( long timestamp ) {
        PacketBuffer buffer;
        boolean ipv6;
        if ( this.getAddress().getAddress() instanceof Inet6Address ) {
            buffer = new PacketBuffer( 294 );
            ipv6 = true;
        } else if ( this.getAddress().getAddress() instanceof Inet4Address ) {
            buffer = new PacketBuffer( 94 );
            ipv6 = false;
        } else {
            // WTF is this IP version?
            this.getImplementationLogger().warn( "Unknown IP version for {}", this.getAddress().getAddress() );
            return;
        }

        buffer.writeByte( CONNECTION_REQUEST_ACCEPTED );            // Packet ID
        buffer.writeAddress( this.getAddress() );                   // Remote system address
        buffer.writeShort( (short) 0 );                             // Remote system index (not applicable)
        for ( int i = 0; i < MAX_LOCAL_IPS; ++i ) {                 // Local IP Addresses
            if ( ipv6 ) {
                buffer.writeAddress( LOCAL_IP_ADDRESSES_V6[i] );
            } else {
                buffer.writeAddress( LOCAL_IP_ADDRESSES[i] );
            }
        }

        buffer.writeLong( timestamp );                              // Timestamp (used for latency detection)
        buffer.writeLong( System.currentTimeMillis() );             // Current Time (used for latency detection)

        // Send to client reliably!
        this.send( PacketReliability.RELIABLE, 0, buffer.getBuffer(), 0, buffer.getPosition() );
    }

    private void sendConnectionRequestFailed() {
        // Simply send NO_FREE_INCOMING_CONNECTIONS
        this.sendNoFreeIncomingConnections();
    }

    @Override
    protected void preUpdate( long time ) {
        super.preUpdate( time );

        // When we did not get a packet in the last 2 seconds send detect lost connection
        if ( this.isConnected() && this.getLastReceivedPacketTime() + 2000L < time && this.lastConnectionLostCheck + 2000L < time ) {
            this.sendDetectLostConnection();
            this.lastConnectionLostCheck = time;
        }
    }

    @Override
    protected void postUpdate( long time ) {
        super.postUpdate( time );

        if ( this.lastTrafficStats + 1000L < time ) {
            this.lastTrafficStats = time;

            this.getImplementationLogger().trace( "Traffic stats: {} bytes tx {} bytes rx", this.bytesSend.getAndSet( 0 ), this.bytesReceived.getAndSet( 0 ) );
        }
    }
}
