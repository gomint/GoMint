/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.math;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public final class MathUtils {

    private static final int BIG_ENOUGH_INT = 30_000_000;
    private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
    private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5;

    private MathUtils() {
        throw new AssertionError( "Cannot instantiate MathUtils!" );
    }

    /**
     * Clamps the specified value into the given range.
     *
     * @param v   The value to be clamped
     * @param min The minimum value to clamp to
     * @param max The maximum value to clamp to
     * @return The clamped value
     */
    public static int clamp( int v, int min, int max ) {
        return ( v < min ? min : ( v > max ? max : v ) );
    }

    /**
     * Clamps the specified value into the given range.
     *
     * @param v   The value to be clamped
     * @param min The minimum value to clamp to
     * @param max The maximum value to clamp to
     * @return The clamped value
     */
    public static float clamp( float v, float min, float max ) {
        return ( v < min ? min : ( v > max ? max : v ) );
    }

    /**
     * Clamps the specified value into the given range.
     *
     * @param v   The value to be clamped
     * @param min The minimum value to clamp to
     * @param max The maximum value to clamp to
     * @return The clamped value
     */
    public static double clamp( double v, double min, double max ) {
        return ( v < min ? min : ( v > max ? max : v ) );
    }

    public static float square( float in ) {
        return in * in;
    }

    public static int fastFloor( float x ) {
        return (int) ( x + BIG_ENOUGH_FLOOR ) - BIG_ENOUGH_INT;
    }

    public static int fastRound( float x ) {
        return (int) ( x + BIG_ENOUGH_ROUND ) - BIG_ENOUGH_INT;
    }

    public static int fastCeil( float x ) {
        return BIG_ENOUGH_INT - (int) ( BIG_ENOUGH_FLOOR - x );
    }

    /**
     * Accurate approximation for a floating-point square root.
     * Roughly 1.2x as fast as java.lang.Math.sqrt(x);
     *
     * @param number which should be square rooted
     * @return float square root
     */
    public static float sqrt( float number ) {
        final float xhalf = number * 0.5F;
        float y = Float.intBitsToFloat( 0x5f375a86 - ( Float.floatToIntBits( number ) >> 1 ) );
        y = y * ( 1.5F - ( xhalf * y * y ) );
        y = y * ( 1.5F - ( xhalf * y * y ) );
        return number * y;
    }

}
