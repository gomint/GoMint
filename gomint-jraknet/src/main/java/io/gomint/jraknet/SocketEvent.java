package io.gomint.jraknet;

import java.net.SocketAddress;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class SocketEvent {

	public enum Type {

		/**
		 * Notifies that a new incoming connection was established and is ready for further use. Events of this
		 * type are guaranteed to possess a connection parameter.
		 */
		NEW_INCOMING_CONNECTION,

		/**
		 * Notifies that a connection was closed by the remote host. Events of this type are guaranteed to possess a
		 * connection parameter. For connections that closed but were disconnected by this side of the network see
		 * the {@link #CONNECTION_DISCONNECTED} event.
		 */
		CONNECTION_CLOSED,

		/**
		 * Notifies that a connection was disconnected by this side of the network. Events of this type are guaranteed
		 * to possess a connection parameter. For connections closed by the remote peer see the {@link #CONNECTION_CLOSED}
		 * event.
		 */
		CONNECTION_DISCONNECTED,

		/**
		 * Notifies that a client connection received an unconnected pong from a target system. Events of this type are
		 * guaranteed to possess a ping pong info parameter.
		 */
		UNCONNECTED_PONG,

		/**
		 * Notifies that a client connection's connection attempt failed. Events of this type are guaranteed to possess
		 * a reason why the connection attempt failed.
		 */
		CONNECTION_ATTEMPT_FAILED,

		/**
		 * Notifies that a client connection's connection attempt succeeded. Events of this type are guaranteed
		 * to possess a connection parameter.
		 */
		CONNECTION_ATTEMPT_SUCCEEDED,

		/**
		 * Notifies that a server socket has gotten a unconnected ping. Events of this type are guaranteed to possess
		 * data which can be modified to the unconnected pong response (motd)
         */
		UNCONNECTED_PING;

	}

	public static final class PingPongInfo {

		private final SocketAddress address;
		private final long          pingTime;
		private final long          pongTime;
		private final long          remoteGuid;
		private String              motd;

		PingPongInfo( final SocketAddress address, final long pingTime, final long pongTime, final long remoteGuid, final String motd ) {
			this.address = address;
			this.pingTime = pingTime;
			this.pongTime = pongTime;
			this.remoteGuid = remoteGuid;
			this.motd = motd;
		}

		public SocketAddress getAddress() {
			return this.address;
		}

		public long getPingTime() {
			return this.pingTime;
		}

		public long getPongTime() {
			return this.pongTime;
		}

		public long getRemoteGuid() {
			return this.remoteGuid;
		}

		public String getMotd() {
			return this.motd;
		}

		public void setMotd(String motd) {
			this.motd = motd;
		}

	}

	private final Type   type;
	private       Object data;

	SocketEvent( Type type ) {
		this.type = type;
	}

	SocketEvent( Type type, Connection connection ) {
		this.type = type;
		this.data = connection;
	}

	SocketEvent( Type type, PingPongInfo info ) {
		this.type = type;
		this.data = info;
	}

	SocketEvent( Type type, String reason ) {
		this.type = type;
		this.data = reason;
	}

	/**
	 * Gets the type of the socket event.
	 *
	 * @return The type of the socket event
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Returns the connection of the event if applicable or null otherwise.
	 *
	 * @return The connection of the event
	 */
	public Connection getConnection() {
		if ( this.type == Type.NEW_INCOMING_CONNECTION ||
		     this.type == Type.CONNECTION_CLOSED ||
		     this.type == Type.CONNECTION_DISCONNECTED ||
			 this.type == Type.CONNECTION_ATTEMPT_SUCCEEDED ) {
			return (Connection) this.data;
		}
		return null;
	}

	/**
	 * Returns the ping pong information contained of this event if applicable or null otherwise.
	 *
	 * @return The ping pong information of the event
	 */
	public PingPongInfo getPingPongInfo() {
		if ( this.type == Type.UNCONNECTED_PONG || this.type == Type.UNCONNECTED_PING ) {
			return (PingPongInfo) this.data;
		}
		return null;
	}

	/**
	 * Gets the reason of the event if applicable or null otherwise.
	 *
	 * @return The reason of the event
	 */
	public String getReason() {
		if ( this.type == Type.CONNECTION_ATTEMPT_FAILED ) {
			return (String) this.data;
		}
		return null;
	}

}
