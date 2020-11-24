/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test;import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TestColorStrip {

    public static final char COLOR_CHAR = '\u00A7';
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile( "(?i)" + String.valueOf( COLOR_CHAR ) + "[0-9A-FK-OR]" );

    @Test
    public void colorStrip() {
        Assertions.assertEquals( regexFilter(), iterationFilter() );
    }

    public String regexFilter() {
        return STRIP_COLOR_PATTERN.matcher( "jkldsa§c§c§§" ).replaceAll( "" );
    }

    public String iterationFilter() {
        String input = "jkldsa§c§c§§";
        int index = 0;
        char[] output = new char[input.length()];

        for ( int i = 0; i < input.length(); i++ ) {
            char c = input.charAt( i );
            if ( c == COLOR_CHAR ) {
                // Check next char
                if ( input.length() > i + 1 ) {
                    char nc = input.charAt( i + 1 );
                    if ( ( nc >= '0' && nc <= '9' ) ||
                        ( nc >= 'A' && nc <= 'F' ) ||
                        ( nc >= 'a' && nc <= 'f' ) ||
                        ( nc >= 'K' && nc <= 'O' ) ||
                        ( nc >= 'k' && nc <= 'o' ) ||
                        nc == 'R' || nc == 'r' ) {
                        i++;
                    } else {
                        output[index++] = c;
                    }
                } else {
                    output[index++] = c;
                }
            } else {
                output[index++] = c;
            }
        }

        return new String( output, 0, index );
    }

}
