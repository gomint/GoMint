package io.gomint.jraknet.datastructures;

/**
 * @author BlackyPaw
 * @version 1.0
 */
// Only deprecated in order to hide it from the JavaDoc
// @Deprecated
public class DatagramContentNode {

	private int reliableMessageNumber;
	private DatagramContentNode next;

	public DatagramContentNode() {
		this( 0, null );
	}

	public DatagramContentNode( int reliableMessageNumber ) {
		this( reliableMessageNumber, null );
	}

	public DatagramContentNode( int reliableMessageNumber, DatagramContentNode next ) {
		this.reliableMessageNumber = reliableMessageNumber;
		this.next = next;
	}

	public int getReliableMessageNumber() {
		return reliableMessageNumber;
	}

	public void setReliableMessageNumber( int reliableMessageNumber ) {
		this.reliableMessageNumber = reliableMessageNumber;
	}

	public DatagramContentNode getNext() {
		return next;
	}

	public void setNext( DatagramContentNode next ) {
		this.next = next;
	}
}
