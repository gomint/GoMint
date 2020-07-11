/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PerformanceHacks {

    private static final Logger LOGGER = LoggerFactory.getLogger( PerformanceHacks.class );
    private static Object unsafe;

    static {
        // CHECKSTYLE:OFF
        try {
            Class unsafeClass = Class.forName( "sun.misc.Unsafe" );
            getUnsafeField( unsafeClass );
        } catch ( ClassNotFoundException e1 ) {

        }
        // CHECKSTYLE:ON
    }

    private static void getUnsafeField( Class unsafeClass ) {
        // CHECKSTYLE:OFF
        try {
            Field f = unsafeClass.getDeclaredField( "theUnsafe" );
            f.setAccessible( true );
            unsafe = f.get( null );
            LOGGER.info( "Got unsafe memory access" );
        } catch ( Exception e ) {
            // Ignore this, this is optional unsafe getting stuff
        }
        // CHECKSTYLE:ON
    }

    /**
     * Return the unsafe instance or null when the JVM has none
     *
     * @return unsafe instance or null
     */
    public static Object getUnsafe() {
        return unsafe;
    }

}
