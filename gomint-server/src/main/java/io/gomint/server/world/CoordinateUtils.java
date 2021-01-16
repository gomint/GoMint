/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.math.BlockPosition;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public final class CoordinateUtils {

    /**
     * No init!
     */
    private CoordinateUtils() {
        throw new AssertionError( "Cannot instantiate CoordinateUtils!" );
    }

    /**
     * Converts a block coordinate to a chunk section coordinate.
     *
     * @param v The value to be converted
     * @return The converted value
     */
    public static int fromBlockToChunk( int v ) {
        return v >> 4;
    }

    /**
     * Gets the minimum block coordinate the specified chunk section contains.
     *
     * @param v The coordinate of the chunk section
     * @return The minimum block coordinate contained in the chunk section
     */
    public static int getChunkMin( int v ) {
        return v << 4;
    }

    /**
     * Gets the maximum block coordinate the specified chunk section contains.
     *
     * @param v The coordinate of the chunk section
     * @return The maximum block coordinate contained in the chunk section
     */
    public static int getChunkMax( int v ) {
        return ( ( v + 1 ) << 4 ) - 1;
    }

    /**
     * Converts a chunk's coordinate to a region file coordinate. Only suitable for x- and z-coordinates.
     *
     * @param v The value to be converted
     * @return The converted value
     */
    public static int fromChunkToRegion( int v ) {
        return v >> 5;
    }

    /**
     * Shift two int's together to form a compound key
     *
     * @param x value of key
     * @param z value of key
     * @return long compound of the two int's
     */
    public static long toLong( int x, int z ) {
        return ( (long) x << 32 ) + z - Integer.MIN_VALUE;
    }

    /**
     * Shift three int's together to form a compound key
     *
     * @param position which should be used
     * @return long compound of the three int's
     */
    public static long toLong( BlockPosition position ) {
        return ( ( (long) position.x() & 0x3FFFFFF ) << 38 ) | ( ( (long) position.y() & 0xFFF ) << 26 ) | ( (long) position.z() & 0x3FFFFFF );
    }

    /**
     * Shift three int's together to form a compound key
     *
     * @param x
     * @param y
     * @param z
     * @return long compound of the three int's
     */
    public static long toLong( int x, int y, int z ) {
        return ( ( (long) x & 0x3FFFFFF ) << 38 ) | ( ( (long) y & 0xFFF ) << 26 ) | ( (long) z & 0x3FFFFFF );
    }

    /**
     * Get the vector which has been encoded into the long
     *
     * @param hash The encoded long
     * @return the decoded BlockPosition
     */
    public static BlockPosition fromLong( long hash ) {
        int x = (int) ( hash >> 38 );
        int y = (int) ( hash >> 26 ) & 0xFFF;
        int z = (int) ( hash ) & 0x3FFFFFF;

        return new BlockPosition( x, y, z );
    }

}
