/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

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

}
