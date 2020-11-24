/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.util.random;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public final class FastRandom extends java.util.Random {

    private static final ThreadLocal<FastRandom> FAST_RANDOM_THREAD_LOCAL = new ThreadLocal<>();

    private long seed;

    /**
     * Generate a new fast random with System.nanoTime() as seed
     */
    public FastRandom() {
        this( System.nanoTime() );
    }

    /**
     * Generate new fast random with given seed
     *
     * @param seed the initial seed
     */
    public FastRandom( long seed ) {
        this.seed = seed;
    }

    /**
     * Set the seed of this random
     *
     * @param seed which should be used
     */
    @Override
    public void setSeed( long seed ) {
        this.seed = seed;
    }

    /**
     * Implementation of George Marsaglia's elegant Xorshift random generator
     * 30% faster and better quality than the built-in java.util.random see also
     * see http://www.javamex.com/tutorials/random_numbers/xorshift.shtml
     *
     * @param nbits number of bits to generate
     * @return randomized number of bits length
     */
    @Override
    protected int next( int nbits ) {
        long x = this.seed;
        x ^= ( x << 21 );
        x ^= ( x >>> 35 );
        x ^= ( x << 4 );
        this.seed = x;
        x &= ( ( 1L << nbits ) - 1 );

        return (int) x;
    }

    /**
     * Thread safe fast random access
     *
     * @return a per thread instance of the fast random
     */
    public static FastRandom current() {
        FastRandom fastRandom = FAST_RANDOM_THREAD_LOCAL.get();
        if ( fastRandom == null ) {
            fastRandom = new FastRandom();
            FAST_RANDOM_THREAD_LOCAL.set( fastRandom );
        }

        return fastRandom;
    }

}
