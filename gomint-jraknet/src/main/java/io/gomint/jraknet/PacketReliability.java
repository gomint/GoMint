package io.gomint.jraknet;

/**
 * Different types of reliability which may be assured for sending data across the network.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public enum PacketReliability {

	/**
	 * No guarantees about the arrival of the data is made.
	 */
	UNRELIABLE( (byte) 0 ),

	/**
	 * No guarantees about the arrival of the data is made but if it arrives
	 * only the newest data will actually be considered. Older data packets
	 * get discarded.
	 */
	UNRELIABLE_SEQUENCED( (byte) 1 ),

	/**
	 * Ensures the data arrives at its destination (at any point in the future).
	 */
	RELIABLE( (byte) 2 ),

	/**
	 * Ensures the data arrives at its destination and all packets will be received
	 * in order.
	 */
	RELIABLE_ORDERED( (byte) 3 ),

	/**
	 * Ensures the data arrives at its destination and only the newest data will be considered
	 * (similar to UNRELIABLE_SEQUENCED).
	 */
	RELIABLE_SEQUENCED( (byte) 4 ),

	/**
	 * Unsupported.
	 */
	UNRELIABLE_WITH_ACK_RECEIPT( (byte) 5 ),

	/**
	 * Unsupported.
	 */
	RELIABLE_WITH_ACK_RECEIPT( (byte) 6 ),

	/**
	 * Unsupported.
	 */
	RELIABLE_ORDERED_WITH_ACK_RECEIPT( (byte) 7 );

	private final byte id;

	PacketReliability( byte id ) {
		this.id = id;
	}

	public static PacketReliability getFromId( byte id ) {
		// Don't use static HashMap for just those 8 values:
		switch ( id ) {
			case 0:
				return UNRELIABLE;
			case 1:
				return UNRELIABLE_SEQUENCED;
			case 2:
				return RELIABLE;
			case 3:
				return RELIABLE_ORDERED;
			case 4:
				return RELIABLE_SEQUENCED;
			case 5:
				return UNRELIABLE_WITH_ACK_RECEIPT;
			case 6:
				return RELIABLE_WITH_ACK_RECEIPT;
			case 7:
				return RELIABLE_ORDERED_WITH_ACK_RECEIPT;
			default:
				return null;
		}
	}

	public byte getId() {
		return this.id;
	}

}
