package io.gomint.server.util.collection;

import java.util.Arrays;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LongList {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final long[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private long[] elementData;
    private int size;

    public LongList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public void add( long element ) {
        ensureCapacityInternal( this.size + 1 );
        elementData[size++] = element;
    }

    private static int hugeCapacity( int minCapacity ) {
        if ( minCapacity < 0 ) {
            throw new OutOfMemoryError();
        }
        
        return ( minCapacity > MAX_ARRAY_SIZE ) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    private void grow( int minCapacity ) {
        // overflow-conscious code
        int oldCapacity = this.elementData.length;
        int newCapacity = oldCapacity + ( oldCapacity >> 1 );
        if ( newCapacity - minCapacity < 0 ) {
            newCapacity = minCapacity;
        }
        
        if ( newCapacity - MAX_ARRAY_SIZE > 0 ) {
            newCapacity = hugeCapacity( minCapacity );
        }

        // minCapacity is usually close to size, so this is a win:
        this.elementData = Arrays.copyOf( this.elementData, newCapacity );
    }

    private void ensureExplicitCapacity( int minCapacity ) {
        // overflow-conscious code
        if ( minCapacity - this.elementData.length > 0 ) {
            grow( minCapacity );
        }
    }

    private void ensureCapacityInternal( int minCapacity ) {
        if ( this.elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA ) {
            minCapacity = Math.max( DEFAULT_CAPACITY, minCapacity );
        }

        ensureExplicitCapacity( minCapacity );
    }

    /**
     * Return the size of the data stored in the list
     *
     * @return amount of data stored
     */
    public int size() {
        return this.size;
    }

    /**
     * Remove the last element in the array
     *
     * @return the removed element
     */
    public long remove() {
        return this.elementData[--size];
    }

    public boolean contains( long value ) {
        for ( int i = 0; i < this.size; i++ ) {
            if ( this.elementData[i] == value ) {
                return true;
            }
        }

        return false;
    }

}
