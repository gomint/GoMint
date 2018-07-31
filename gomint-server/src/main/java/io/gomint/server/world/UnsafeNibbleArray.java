/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.util.PerformanceHacks;
import io.gomint.server.util.performance.UnsafeAllocator;
import sun.misc.Unsafe;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UnsafeNibbleArray implements NibbleArray {

    private static final Unsafe UNSAFE = (Unsafe) PerformanceHacks.getUnsafe();

    private long memoryAddress;
    private int length;

    /**
     * Allocates a new nibble array that is able to hold length entries.
     *
     * @param length The desired length of the array
     */
    public UnsafeNibbleArray( short length ) {
        this.length = length;
        this.memoryAddress = UnsafeAllocator.allocate( ( length + 1 ) >> 1 );
    }

    @Override
    public void set( int index, byte value ) {
        value &= 0xF;

        byte data = UNSAFE.getByte( this.memoryAddress + ( index / 2 ) );

        data &= (byte) ( 0xF << ( ( index + 1 ) % 2 * 4 ) );
        data |= (byte) ( value << ( index % 2 * 4 ) );

        UNSAFE.putByte( this.memoryAddress + ( index / 2 ), data );
    }

    @Override
    public byte get( int index ) {
        byte data = UNSAFE.getByte( this.memoryAddress + ( index / 2 ) );
        return (byte) ( data >> ( ( index & 1 ) << 2 ) & 0xF );
    }

    @Override
    public byte[] raw() {
        byte[] data = new byte[this.length / 2];
        for ( int i = 0; i < data.length; i++ ) {
            data[i] = UNSAFE.getByte( this.memoryAddress + i );
        }

        return data;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    protected void finalize() throws Throwable {
        UnsafeAllocator.freeMemory( this.memoryAddress );
    }

}
