/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

import io.gomint.server.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TestStringUtil {

    @Test
    public void testRandomOutput() {
        for ( int i = 0; i < 500; i++ ) {
            String str = randomString( 32 );
            byte[] a = str.getBytes( StandardCharsets.UTF_8 );
            byte[] b = StringUtil.getUTF8Bytes( str );

            Assert.assertArrayEquals( a, b );
        }
    }

    public static String randomString( int length ) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder( length );
        while ( sb.length() < length ) {
            char c = (char) ( rand.nextInt() );
            if ( Character.isDefined( c ) ) {
                sb.append( c );
            }
        }

        return sb.toString();
    }

}
