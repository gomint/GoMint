package io.gomint.jraknet;

/**
 * Functional interface for socket event handlers.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public interface SocketEventHandler {

	void onSocketEvent( Socket socket, SocketEvent event );

}
