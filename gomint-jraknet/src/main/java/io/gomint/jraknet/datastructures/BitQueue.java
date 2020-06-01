package io.gomint.jraknet.datastructures;

/**
 * Implementation of a queue that compresses multiple single bits (booleans) into single bytes.
 * This queue saves a lot of memory per element as it does not incur the cost of creating primitive
 * wrappers that are required by the JDK's queue implementations.
 * <p>
 * The implementation offers all common queue operations such as add, peek and poll but also allows
 * for random access on all elements that are currently contained in the queue via set and get.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class BitQueue {

	private byte[] buffer;

	private int head;
	private int tail;

	/**
	 * Constructs a new bit queue which will be able to hold at least capacity bits on creation.
	 *
	 * @param capacity The initial capacity of the queue
	 */
	public BitQueue( int capacity ) {
		assert ( ( capacity != 0 ) && ( ( capacity & ( ~capacity + 1 ) ) == capacity ) )
				: "Capacity must be power of two";
		if ( capacity <= 0 ) {
			capacity = 8;
		}

		this.buffer = new byte[( ( capacity + 7 ) >> 3 )];
		this.head = 0;
		this.tail = 0;
	}

	/**
	 * Adds the specified bit to the queue.
	 *
	 * @param bit The bit to add to the queue
	 */
	public void add( boolean bit ) {
		if ( ( ( this.head + 1 ) & ( ( this.buffer.length << 3 ) - 1 ) ) == this.tail ) {
			// Queue is full --> Reallocate:
			this.reallocate( this.buffer.length << 4 );
		}

		int  by = this.head >> 3;
		byte bi = (byte) ( 1 << ( this.head & 7 ) );
		this.buffer[by] ^= (byte) ( ( ( bit ? 0xFF : 0x00 ) ^ this.buffer[by] ) & bi );
		this.head = ( this.head + 1 ) & ( ( this.buffer.length << 3 ) - 1 );
	}

	/**
	 * Internal method that resizes the underlying buffer and copies all bits currently in queue
	 * to their respective places inside the newly allocated byte buffer. In case the queue's current
	 * tail pointer is aligned to a byte boundary a faster immediate array copy will be preferred over
	 * an 8-bits at a time approach that spans the boundary between two bytes.
	 *
	 * @param capacity The capacity of the new buffer to fill
	 */
	private void reallocate( int capacity ) {
		byte[] b = new byte[( capacity + 7 ) >> 3];

		// Check if we may perform an aligned copy:
		if ( ( this.tail & 7 ) == 0 ) {
			int size = this.size();
			if ( this.head > this.tail ) {
				int by = this.tail >> 3;
				int bl = ( this.head - this.tail + 7 ) >> 3;
				System.arraycopy( this.buffer, by, b, 0, bl );
			} else if ( this.head < this.tail ) {
				int by = this.tail >> 3;
				int bl = ( ( this.buffer.length << 3 ) - this.tail + 7 ) >> 3;
				System.arraycopy( this.buffer, by, b, 0, bl );
				by = ( this.head + 7 ) >> 3;
				System.arraycopy( this.buffer, 0, b, bl, by );
			}

			this.tail = 0;
			this.head = size;
		} else {
			// At this point this.head < this.tail will always hold
			// as the only condition where reallocate will be invoked
			// AND this.tail < this.head holds true is when this.tail = 0
			// and this.head = capacity:

			int size = this.size();
			int m    = ( this.tail & 7 );
			int by1  = this.tail >> 3;
			int by2  = ( by1 + 1 ) & ( this.buffer.length - 1 );
			int mask;
			int b1;
			int b2;

			int cursor = 0;
			while ( cursor < size ) {
				mask = ( ( 1 << m ) - 1 ) & 0xFF;
				b1 = ( ( this.buffer[by1] & ( ~mask & 0xFF ) ) >>> m );
				b2 = ( this.buffer[by2] << ( 8 - m ) );
				b[cursor >> 3] = (byte) ( b1 | b2 );

				cursor += 8;
				by1 = ( by1 + 1 ) & ( this.buffer.length - 1 );
				by2 = ( by2 + 1 ) & ( this.buffer.length - 1 );
			}

			this.tail = 0;
			this.head = size;
		}

		this.buffer = b;
	}

	/**
	 * Returns the number of elements reminaing inside the queue.
	 *
	 * @return The queue's size
	 */
	public int size() {
		if ( this.head > this.tail ) {
			return ( this.head - this.tail );
		} else if ( this.head < this.tail ) {
			return ( ( this.buffer.length << 3 ) - ( this.tail - this.head ) );
		} else {
			return 0;
		}
	}

	/**
	 * Sets the n-th bit contained in the queue to the specified value.
	 * <p>
	 * Instead of throwing an Exception this method will simply show no
	 * effect at all if the index is out bounds.
	 *
	 * @param n   The index of the bit to change
	 * @param bit The value to assign to the bit
	 */
	public void set( int n, boolean bit ) {
		if ( n >= this.size() || n < 0 ) {
			return;
		}

		int  idx = ( this.tail + n ) & ( ( this.buffer.length << 3 ) - 1 );
		int  by  = idx >> 3;
		byte bi  = (byte) ( 1 << ( idx & 7 ) );
		this.buffer[by] ^= (byte) ( ( ( bit ? 0xFF : 0x00 ) ^ this.buffer[by] ) & bi );
	}

	/**
	 * Gets the value of the n-th bit contained in the queue.
	 * <p>
	 * If the index should lie out of the queue's bounds false will be returned.
	 *
	 * @param n The index of the bit to get the value of
	 *
	 * @return The value of the n-th bit inside the queue
	 */
	public boolean get( int n ) {
		if ( n >= this.size() || n < 0 ) {
			return false;
		}

		int  idx = ( this.tail + n ) & ( ( this.buffer.length << 3 ) - 1 );
		int  by  = idx >> 3;
		byte bi  = (byte) ( 1 << ( idx & 7 ) );
		return ( this.buffer[by] & bi ) != 0;
	}

	/**
	 * Tests whether or not the queue is currently empty
	 *
	 * @return Whether or not the queue is currently empty
	 */
	public boolean isEmpty() {
		return ( this.head == this.tail );
	}

	/**
	 * Peeks at the next bit to be returned from the queue without actually removing it.
	 * If the queue is empty false will be returned.
	 *
	 * @return The value of the element that would be returned next
	 */
	public boolean peek() {
		if ( this.head == this.tail ) {
			// Queue is empty
			return false;
		}

		int  by = this.tail >> 3;
		byte bi = (byte) ( 1 << ( ( this.tail ) & 7 ) );
		return ( this.buffer[by] & bi ) != 0;
	}

	/**
	 * Gets the value of the next bit to be returned from the queue and removes it entirely.
	 * If the queue is currently empty false will be returned.
	 *
	 * @return The value of the next element in the queue
	 */
	public boolean poll() {
		if ( this.head == this.tail ) {
			// Queue is empty
			return false;
		}

		int  by = this.tail >> 3;
		byte bi = (byte) ( 1 << ( ( this.tail ) & 7 ) );
		this.tail = ( this.tail + 1 ) & ( ( this.buffer.length << 3 ) - 1 );
		return ( this.buffer[by] & bi ) != 0;
	}

}
