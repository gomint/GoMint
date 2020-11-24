/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public enum ChatColor {

    /**
     * Represents black.
     */
    BLACK( '0' ),

    /**
     * Represents dark blue.
     */
    DARK_BLUE( '1' ),

    /**
     * Represents dark green.
     */
    DARK_GREEN( '2' ),

    /**
     * Represents dark blue (aqua).
     */
    DARK_AQUA( '3' ),

    /**
     * Represents dark red.
     */
    DARK_RED( '4' ),

    /**
     * Represents dark purple.
     */
    DARK_PURPLE( '5' ),

    /**
     * Represents gold.
     */
    GOLD( '6' ),

    /**
     * Represents gray.
     */
    GRAY( '7' ),

    /**
     * Represents dark gray.
     */
    DARK_GRAY( '8' ),

    /**
     * Represents blue.
     */
    BLUE( '9' ),

    /**
     * Represents green.
     */
    GREEN( 'a' ),

    /**
     * Represents aqua.
     */
    AQUA( 'b' ),

    /**
     * Represents red.
     */
    RED( 'c' ),

    /**
     * Represents light purple.
     */
    LIGHT_PURPLE( 'd' ),

    /**
     * Represents yellow.
     */
    YELLOW( 'e' ),

    /**
     * Represents white.
     */
    WHITE( 'f' ),

    /**
     * Represents minecoin gold
     */
    MINECOIN_GOLD('g'),

    /**
     * Represents magical characters that change around randomly.
     */
    MAGIC( 'k' ),

    /**
     * Makes the text bold.
     */
    BOLD( 'l' ),

    /**
     * Makes a line appear through the text.
     */
    STRIKETHROUGH( 'm' ),

    /**
     * Makes the text appear underlined.
     */
    UNDERLINE( 'n' ),

    /**
     * Makes the text italic.
     */
    ITALIC( 'o' ),

    /**
     * Resets all previous chat colors or formats.
     */
    RESET( 'r' );

    private static final char COLOR_CHAR = '\u00A7';
    private final String toString;

    /**
     * Constructor which caches the toString view of this color char
     *
     * @param code which is used for caching
     */
    ChatColor( char code ) {
        this.toString = new String( new char[]{ COLOR_CHAR, code } );
    }

    /**
     * Strips the given message of all color codes
     *
     * @param input which should be stripped from all colors
     * @return stripped copy of the input
     */
    public static String stripColor( final String input ) {
        if ( input == null ) {
            return null;
        }

        int index = 0;
        char[] output = new char[input.length()];

        for ( int i = 0; i < input.length(); i++ ) {
            char c = input.charAt( i );
            if ( c == COLOR_CHAR ) {
                // Check next char
                if ( input.length() > i + 1 ) {
                    char nc = input.charAt( i + 1 );
                    if ( ( nc >= '0' && nc <= '9' ) ||
                        ( nc >= 'A' && nc <= 'G' ) ||
                        ( nc >= 'a' && nc <= 'g' ) ||
                        ( nc >= 'K' && nc <= 'O' ) ||
                        ( nc >= 'k' && nc <= 'o' ) ||
                        nc == 'R' || nc == 'r' ) {
                        // CHECKSTYLE:OFF
                        i++;
                        // CHECKSTYLE:ON
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

    /**
     * Translate other char escaped colors into minecraft escaped colors
     *
     * @param altColorChar which has been used to escape colors
     * @param input        which should be processed
     * @return minecraft escaped color string
     */
    public static String translateAlternateColorCodes( char altColorChar, String input ) {
        int index = 0;
        char[] output = new char[input.length()];

        for ( int i = 0; i < input.length(); i++ ) {
            char c = input.charAt( i );
            if ( c == altColorChar ) {
                // Check next char
                if ( input.length() > i + 1 ) {
                    char nc = input.charAt( i + 1 );
                    if ( ( nc >= '0' && nc <= '9' ) ||
                        ( nc >= 'A' && nc <= 'G' ) ||
                        ( nc >= 'a' && nc <= 'g' ) ||
                        ( nc >= 'K' && nc <= 'O' ) ||
                        ( nc >= 'k' && nc <= 'o' ) ||
                        nc == 'R' || nc == 'r' ) {
                        output[index++] = COLOR_CHAR;
                        output[index++] = nc;

                        // CHECKSTYLE:OFF
                        i++;
                        // CHECKSTYLE:ON
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

    @Override
    public String toString() {
        return this.toString;
    }

}
