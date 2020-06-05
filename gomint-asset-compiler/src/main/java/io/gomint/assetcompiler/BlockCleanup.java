/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.assetcompiler;

import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTTagCompound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockCleanup {

    public static void main( String[] args ) throws IOException {
        // Load the MITM assets
        Set<String> knownIDs = new HashSet<>();
        NBTTagCompound assetsCompound = null;

        try {
            assetsCompound = NBTTagCompound.readFrom( new File( "gomint-asset-compiler/input-from-mitm.dat" ), true, ByteOrder.BIG_ENDIAN );
            for ( Object palette : assetsCompound.getList( "blockPalette", false ) ) {
                NBTTagCompound entry = (NBTTagCompound) palette;
                knownIDs.add( entry.getString( "id", null ) );
            }
        } catch ( IOException | AllocationLimitReachedException e ) {
            e.printStackTrace();
            System.exit( -1 );
        }

        List<String> known = new ArrayList<>();

        for ( String line : Files.readAllLines( Paths.get( "blocks.txt" ) ) ) {
            known.add( line );
        }

        try ( FileWriter fileWriter = new FileWriter( "sorted_blocks.txt" ) ) {
            for ( String line : known ) {
                String[] split = line.split( "\\t" );

                String fullID = "minecraft:" + split[2];
                int oldID = Integer.parseInt( split[0] );

                if ( !knownIDs.contains( fullID ) ) {
                    fullID = "???";
                }

                fileWriter.write( oldID + " " + fullID + "\n" );
            }
        }
    }

}
