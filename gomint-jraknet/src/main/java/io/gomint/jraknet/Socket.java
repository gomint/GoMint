package io.gomint.jraknet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 2.0
 */
public abstract class Socket implements AutoCloseable {

    protected Bootstrap udpSocket;
    protected Channel channel;

    // Lifecycle
    private SocketEventHandler eventHandler;

    // RakNet data:
    private long guid;

    // ================================ CONSTRUCTORS ================================ //

    Socket() {

    }

    // ================================ PUBLIC API ================================ //

    /**
     * Checks whether or not the socket has already been initialized for further use. How a socket
     * is initialized depends on its implementation and may be found in the respective documentation.
     *
     * @return Whether or not the socket has already been initialized
     */
    public boolean isInitialized() {
        return ( this.udpSocket != null );
    }

    /**
     * Sets the event handler that will be notified of any interesting events
     * occurring on this socket.
     *
     * @param handler The handler to be notified of any events on this socket
     */
    public void setEventHandler( SocketEventHandler handler ) {
        this.eventHandler = handler;
    }

    /**
     * Returns the socket's GUID.
     * <p>
     * Depending on the implementation this value might change repeatedly.
     *
     * @return The socket's GUID.
     */
    public long getGuid() {
        return this.guid;
    }

    /**
     * Closes the socket and cleans up all internal resources. If any other method is invoked on this
     * socket after a call to this method was made the behaviour of the socket is undefined.
     */
    @Override
    public void close() {
        // Close the UDP socket:
        this.channel.close();
        this.channel = null;
        this.udpSocket = null;
    }

    // ================================ IMPLEMENTATION HOOKS ================================ //

    /**
     * Gets a logger to be used for logging errors and warnings.
     *
     * @return The logger to be used for logging errors and warnings
     */
    protected abstract Logger getImplementationLogger();

    /**
     * Invoked right after a datagram was received. This method may perform very rudimentary
     * datagram handling if necessary.
     *
     * @param sender   The channel which sent this datagram
     * @param datagram The datagram that was just received
     * @return Whether or not the datagram was handled by this method already and should be processed no further
     */
    protected boolean receiveDatagram( InetSocketAddress sender, PacketBuffer datagram ) {
        return false;
    }

    /**
     * Handles the given datagram. This will be invoked on the socket's update thread and should hand
     * this datagram to the connection it belongs to in order to deserialize it appropriately.
     *
     * @param sender   The channel which this datagram sent
     * @param datagram The datagram to be handled
     * @param time     The current system time
     */
    protected abstract void handleDatagram( InetSocketAddress sender, PacketBuffer datagram, long time );

    // ================================ INTERNALS ================================ //

    /**
     * Generates a new GUID for this socket. Must be invoked with care as incoming connections could
     * potentially receive different GUIDs for the same server if this method is invoked if there
     * are connections that have already been established.
     */
    protected final void generateGuid() {
        this.guid = ThreadLocalRandom.current().nextLong();
    }

    /**
     * Propagates the given event to the socket's event handler if any such is available.
     *
     * @param event The event to be propagated
     */
    protected final void propagateEvent( SocketEvent event ) {
        if ( this.eventHandler != null ) {
            this.eventHandler.onSocketEvent( this, event );
        }
    }

    protected void flush( Flusher.FlushItem item ) {
        EventLoops.FLUSHER.queued.add( item );
        EventLoops.FLUSHER.start();
    }

}
