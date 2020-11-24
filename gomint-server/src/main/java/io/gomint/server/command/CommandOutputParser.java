/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.command;

import java.util.Iterator;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandOutputParser {

    public static String parse( String format, List<String> params ) {
        StringBuilder output = new StringBuilder();
        Iterator<String> paramIterator = params.iterator();

        // We currently only translate '%%s' since it seems the only format MC:BE understands
        for ( int i = 0; i < format.length(); i++ ) {
            char c = format.charAt( i );
            if ( c == '%' ) {
                // Do we have 2 chars remaining?
                if ( format.length() - ( i + 1 ) >= 2 ) {
                    // Do we have params to pass in left?
                    if ( paramIterator.hasNext() ) {
                        output.append( paramIterator.next() );
                        i += 2;
                    } else {
                        output.append( c );
                    }
                } else {
                    output.append( c );
                }
            } else {
                output.append( c );
            }
        }

        return output.toString();
    }

}
