/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.maintenance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NetworkPerformance {

    private static final Logger LOGGER = LoggerFactory.getLogger( NetworkPerformance.class );

    private static final long[] DECRYPT_TIMERS = new long[500];
    private static int DECRYPT_TIMERS_INDEX = 0;

    private static final long[] DECOMPRESS_TIMERS = new long[500];
    private static int DECOMPRESS_TIMERS_INDEX = 0;

    public static synchronized void addReceiveTimings( long decryptTime, long decompressTime ) {
        if ( DECRYPT_TIMERS_INDEX == 499 ) {
            DECRYPT_TIMERS_INDEX = 0;
        }

        if ( DECOMPRESS_TIMERS_INDEX == 499 ) {
            DECOMPRESS_TIMERS_INDEX = 0;
        }

        DECRYPT_TIMERS[DECRYPT_TIMERS_INDEX++] = decryptTime;
        DECOMPRESS_TIMERS[DECOMPRESS_TIMERS_INDEX++] = decompressTime;

        // Debug
        long averageDecrypt = 0;
        for ( long decryptTimer : DECRYPT_TIMERS ) {
            averageDecrypt += decryptTimer;
        }

        long averageDecompress = 0;
        for ( long decompressTimer : DECOMPRESS_TIMERS ) {
            averageDecompress += decompressTimer;
        }

        LOGGER.info( "RECV: Decrypt {}ns - Decompress {}ns", ( averageDecrypt / 500d ), ( averageDecompress / 500d ) );
    }

}
