package io.gomint.jraknet;

/**
 * Possible states a connection may be in. Usually connections will only be announced for use once they reached the
 * CONNECTED state.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public enum ConnectionState {

	/**
	 * State of a connection that was just opened due to a received
	 * unreliable UDP packet and did not send any connection requests yet.
	 */
	UNCONNECTED( false ),

	/**
	 * State of a connection that is trying to establish a reliable connection.
	 */
	INITIALIZING( false ),

	/**
	 * State of a connection that provides reliable message transfer but is not
	 * yet officially connected.
	 */
	RELIABLE( true ),

	/**
	 * State of a connection that is currently trying to connect (sending MTU
	 * detection packets and/or connection requests)
	 */
	CONNECTING( true ),

	/**
	 * State of a connection that is fully established and may send reliable
	 * packets. Connected packets will be required to send a connected ping
	 * every second.
	 */
	CONNECTED( true ),

	/**
	 * State of a connection that is currently disconnecting from its remote peer.
	 */
	DISCONNECTING( true );

	private boolean reliable;

	ConnectionState( boolean reliable ) {
		this.reliable = reliable;
	}

	public boolean isReliable() {
		return this.reliable;
	}

}
