package io.gomint.jraknet.datastructures;

/**
 * This class provides a fixed-size round-robin buffer implementation.
 * <p>
 * Whenever an index is required by any methods of this class the index will be
 * normalized into the buffer's capacity bounds in a round-robin-like fashion.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class FixedSizeRRBuffer<T> {

	private Object[] buffer;
	private int      mask;

	/**
	 * Allocates a new fixed size round-robin buffer with the specified capacity that MUST be a power of two (!).
	 *
	 * @param capacity The capacity to allocate
	 */
	public FixedSizeRRBuffer( int capacity ) {
		assert ( ( capacity != 0 ) && ( ( capacity & ( ~capacity + 1 ) ) == capacity ) )
				: "Capacity must be power of two";
		this.buffer = new Object[capacity];
		this.mask = ( capacity - 1 );
	}

	/**
	 * Gets the element at index.
	 *
	 * @param index The index of the element
	 *
	 * @return The value of the element or null if no element at index exists
	 */
	@SuppressWarnings( "unchecked" )
	public T get( int index ) {
		return (T) ( this.buffer[index & this.mask] );
	}

	/**
	 * Sets the value of the element at the specified index.
	 *
	 * @param index The index of the element to set
	 * @param val   The value to set the element to
	 */
	public void set( int index, T val ) {
		this.buffer[index & this.mask] = val;
	}

	/**
	 * Tries to set the value of the element at the specified index. If there already is an
	 * element at the specified index false will be returned. Otherwise the given value will
	 * be stored at the specified index and true will be returned.
	 *
	 * @param index The index of the element to set
	 * @param val   The value to set the element to
	 */
	public boolean trySet( int index, T val ) {
		if ( this.buffer[index & this.mask] != null ) {
			return false;
		}
		this.buffer[index & this.mask] = val;
		return true;
	}

	/**
	 * Removes (nulls out) the element at the specified index.
	 *
	 * @param index The index of the element to remove
	 */
	public void remove( int index ) {
		this.buffer[index & this.mask] = null;
	}

}
