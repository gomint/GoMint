/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.util.PerformanceHacks;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface NibbleArray {

    /**
     * Create a new heap allocated nibble array holding the data given
     *
     * @param data which should be inside the nibble array
     * @return a nibble array which is stored on heap
     */
    static NibbleArray create( byte[] data ) {
        return new HeapNibbleArray( data );
    }

    /**
     * Allocates a new nibble array that is able to hold length entries.
     *
     * @param size The desired length of the array
     */
    static NibbleArray create( short size ) {
        return PerformanceHacks.isUnsafeEnabled() ? new UnsafeNibbleArray( size ) : new HeapNibbleArray( size );
    }

    /**
     * Sets the entry at the specified index
     *
     * @param index The index of the nibble to be set
     * @param value The value to set
     */
    void set( int index, byte value );

    /**
     * Gets the entry at the specified index
     *
     * @param index The index of the nibble to get
     * @return The nibble's value
     */
    byte get( int index );

    /**
     * Gets the raw byte array held by this nibble array
     *
     * @return The raw byte array
     */
    byte[] raw();

    /**
     * Gets the length of the nibble array
     *
     * @return The length of the nibble array
     */
    int length();

}
