/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.NibbleArray;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import com.koloboke.collect.map.hash.HashLongObjMaps;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LevelDBChunk extends ChunkAdapter {

    LevelDBChunk( WorldAdapter worldAdapter, int x, int z ) {
        this.world = worldAdapter;
        this.x = x;
        this.z = z;
        this.entities = HashLongObjMaps.newMutableMap();
    }

    /**
     * Calculates the needed data to be saved back to the database
     *
     * @return The data which will be saved in the database for this chunk
     */
    byte[] getSaveData() {
       /* ByteBuffer byteBuffer = ByteBuffer.allocate( this.blocks.length +
                this.data.raw().length +
                this.skyLight.raw().length +
                this.blockLight.raw().length +
                this.height.length() +
                256 );

        byteBuffer.put( this.blocks );
        byteBuffer.put( this.data.raw() );
        byteBuffer.put( this.skyLight.raw() );
        byteBuffer.put( this.blockLight.raw() );
        byteBuffer.put( this.height.toByteArray() );

        for ( int i = 0; i < 256; i++ ) {
            byteBuffer.putInt( ( this.biomes[i] << 24 ) );
        }
*/
        return new byte[]{};
    }

    void loadSection( int sectionY, byte[] chunkData ) {
        ByteBuffer buf = ByteBuffer.wrap( chunkData );

        // First byte is chunk section version
        buf.get();

        // Next 4096 bytes are block data
        byte[] blockData = new byte[4096];
        buf.get( blockData );

        // Next 2048 bytes are metadata
        byte[] metaData = new byte[2048];
        buf.get( metaData );
        NibbleArray meta = new NibbleArray( metaData );

        for ( int j = 0; j < 16; ++j ) {
            for ( int i = 0; i < 16; ++i ) {
                for ( int k = 0; k < 16; ++k ) {
                    int y = ( sectionY << 4 ) + j;
                    int blockIndex = k << 8 | i << 4 | j; // j k i - k j i - i k j -


                    this.setBlock( k, y, i, blockData[blockIndex] );

                    if ( meta.get( blockIndex ) != 0 ) {
                        this.setData( k, y, i, meta.get( blockIndex ) );
                    }
                }
            }
        }
    }

    void loadTileEntities( byte[] tileEntityData ) {
        ByteArrayInputStream bais = new ByteArrayInputStream( tileEntityData );
        while ( bais.available() > 0 ) {
            try {
                NBTTagCompound nbtTagCompound = NBTTagCompound.readFrom( bais, false, ByteOrder.LITTLE_ENDIAN );
                // DumpUtil.dumpNBTCompund( nbtTagCompound );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    void loadEntities( byte[] entityData ) {
        ByteArrayInputStream bais = new ByteArrayInputStream( entityData );
        while ( bais.available() > 0 ) {
            try {
                NBTTagCompound nbtTagCompound = NBTTagCompound.readFrom( bais, false, ByteOrder.LITTLE_ENDIAN );
                // DumpUtil.dumpNBTCompund( nbtTagCompound );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

}