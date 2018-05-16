/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.gomint;

import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkSlice;
import io.gomint.server.world.gomint.io.Section;
import io.gomint.server.world.gomint.io.SectionFile;
import io.gomint.taglib.NBTTagCompound;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class GomintChunkAdapter extends ChunkAdapter {

    /**
     * Load a Chunk from a NBTTagCompound. This is used when loaded from a Regionfile.
     *
     * @param worldAdapter       which loaded this chunk
     * @param x                  position of chunk
     * @param z                  position of chunk
     * @param lastSavedTimestamp timestamp of last save
     */
    GomintChunkAdapter( GomintWorldAdapter worldAdapter, int x, int z, long lastSavedTimestamp ) {
        super( worldAdapter, x, z );
        this.lastSavedTimestamp = lastSavedTimestamp;
        this.loadedTime = worldAdapter.getServer().getCurrentTickTime();
    }

    /**
     * Load chunk from section
     *
     * @param file which should be used to read
     * @throws IOException when data could not be read like expected
     */
    void loadFromSection( SectionFile file ) throws IOException {
        // We can have up to 16 chunk sections
        int currentSection = 0;
        while ( currentSection < 16 ) {
            try ( Section section = file.getSection() ) {
                ByteBuffer in = section.getInput();
                if ( in.capacity() == 0 ) {
                    return;
                }

                ChunkSlice slice = new ChunkSlice( this, currentSection );
                for ( short i = 0; i < 4096; i++ ) {
                    slice.setBlockInternal( i, 0, in.getInt() );
                    slice.setDataInternal( i, 0, (byte) in.getInt() );

                    // Read NBT if needed
                    int nbtLength = in.getInt();
                    if ( nbtLength > 0 ) {
                        byte[] nbtData = new byte[nbtLength];
                        in.get( nbtData );
                        NBTTagCompound compound = NBTTagCompound.readFrom( new ByteArrayInputStream( nbtData ), false, ByteOrder.BIG_ENDIAN );
                        TileEntity tileEntity = TileEntities.construct( compound, this.world );
                        slice.addTileEntityInternal( i, tileEntity );
                    }
                }

                this.chunkSlices[currentSection] = slice;
                currentSection++;
            }
        }

        this.calculateHeightmap( 16 );
    }

    public void saveToSection( SectionFile sectionFile ) throws IOException {
        for ( ChunkSlice slice : this.chunkSlices ) {
            if ( slice == null ) {
                return;
            }

            try ( Section section = sectionFile.createSection() ) {
                DataOutputStream out = section.getOutput();
                for ( short i = 0; i < 4096; i++ ) {
                    out.writeInt( slice.getBlockInternal( 0, i ) );
                    out.writeInt( slice.getDataInternal( 0, i ) );

                    // Do we have a tile here
                    TileEntity tileEntity = slice.getTileInternal( i );
                    if ( tileEntity != null ) {
                        NBTTagCompound compound = new NBTTagCompound( "" );
                        tileEntity.toCompound( compound );
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compound.writeTo( baos, false, ByteOrder.BIG_ENDIAN );
                        byte[] nbtData = baos.toByteArray();
                        out.writeInt( nbtData.length );
                        out.write( nbtData );
                    } else {
                       out.writeInt( 0 );
                    }
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }



}
