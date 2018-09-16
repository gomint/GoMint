/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.assetcompiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCleanup {

    public static void main( String[] args ) throws IOException {
        Set<String> known = new HashSet<>();

        for ( String line : Files.readAllLines( Paths.get( "gomint-asset-compiler/known_1.8.txt" ) ) ) {
            known.add( line );
        }

        for ( String line : Files.readAllLines( Paths.get( "cleanup.txt" ) ) ) {
            if ( !line.isEmpty() ) {
                // We have lines with pictures in front
                String[] split = line.split( "\\t" );
                if ( split[0].equals( "Icon" ) || line.contains( "unused" ) ) {
                    continue;
                }

                String oldID;
                String mcID;
                if ( !split[0].endsWith( ".png" ) ) {
                    // We are only interested in the dec representation and the final mc ID
                    mcID = split[2];
                    oldID = split[0];
                } else {
                    mcID = split[3];
                    oldID = split[1];
                }

                if ( known.contains( mcID ) ) {
                    known.remove( mcID );

                    System.out.println( oldID + " " + mcID );
                }
            }
        }

        for ( String s : known ) {
            System.out.println( s );
        }
    }

}
