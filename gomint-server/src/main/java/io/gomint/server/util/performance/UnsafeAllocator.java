/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import io.gomint.server.util.PerformanceHacks;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.util.HashSet;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UnsafeAllocator {

    private static final Logger LOGGER = LoggerFactory.getLogger( UnsafeAllocator.class );
    private static final Unsafe UNSAFE = (Unsafe) PerformanceHacks.getUnsafe();
    private static final Set<Entry> ALLOCATED_BASES = new HashSet<>();

    /**
     * Allocate a new memory block off heap
     *
     * @param size of the memory block in bytes
     * @return base address of the memory block
     */
    public static synchronized long allocate( int size ) {
        long baseAddress = UNSAFE.allocateMemory( size );
        ALLOCATED_BASES.add( new Entry( baseAddress, size ) );
        return baseAddress;
    }

    /**
     * Print usage
     */
    public static void printUsage() {
        try {
            long bytes = getAllocatedBytes();
            String humanReadableVersion;

            int unit = 1024;
            if ( bytes < unit ) {
                humanReadableVersion = bytes + " B";
            } else {
                int exp = (int) ( Math.log( bytes ) / Math.log( unit ) );
                String pre = ( "KMGTPE" ).charAt( exp - 1 ) + "i";
                humanReadableVersion = String.format( "%.1f %sB", bytes / Math.pow( unit, exp ), pre );
            }

            LOGGER.info( "Currently allocated {} ({} B) off-heap", humanReadableVersion, bytes );
        } catch ( Throwable t ) {
            LOGGER.warn( "Could not print allocation stats", t );
        }
    }

    /**
     * Get current allocated bytes from this allocator
     *
     * @return bytes currently allocated
     */
    private static synchronized long getAllocatedBytes() {
        long sum = 0;

        for ( Entry entry : ALLOCATED_BASES ) {
            sum += entry.getValue();
        }

        return sum;
    }

    /**
     * Free allocated memory
     *
     * @param baseAddress of the memory block
     */
    public static synchronized void freeMemory( long baseAddress ) {
        UNSAFE.freeMemory( baseAddress );
        ALLOCATED_BASES.remove( new Entry( baseAddress, 0 ) );
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode( of = { "key" } )
    private static class Entry {
        private final long key;
        private final int value;
    }

}
