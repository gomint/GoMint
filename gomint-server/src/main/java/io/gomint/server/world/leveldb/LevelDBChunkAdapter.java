/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.util.Pair;
import io.gomint.server.util.Palette;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.HeapNibbleArray;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.postprocessor.PistonPostProcessor;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTReaderNoBuffer;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LevelDBChunkAdapter extends ChunkAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger( LevelDBChunkAdapter.class );
    private int chunkVersion;

    /**
     * Create a new level db backed chunk
     *
     * @param worldAdapter which loaded this chunk
     * @param x            position of chunk
     * @param z            position of chunk
     * @param chunkVersion version of this chunk
     */
    public LevelDBChunkAdapter( WorldAdapter worldAdapter, int x, int z, byte chunkVersion ) {
        super( worldAdapter, x, z );
        this.chunkVersion = chunkVersion;
    }

    public LevelDBChunkAdapter( WorldAdapter worldAdapter, int x, int z ) {
        super( worldAdapter, x, z );
        this.chunkVersion = 8;
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
        PacketBuffer buffer = new PacketBuffer( chunkData, 0 );

        // First byte is chunk section version
        byte subchunkVersion = buffer.readByte();
        int storages = 1;
        switch ( subchunkVersion ) {
            case 8:
                storages = buffer.readByte();
            case 1:
                for ( int sI = 0; sI < storages; sI++ ) {
                    byte data = buffer.readByte();
                    boolean isPersistent = ( ( data >> 8 ) & 1 ) != 1; // last bit is the isPresent state (shift and mask it to 1)
                    byte wordTemplate = (byte) ( data >>> 1 ); // Get rid of the last bit (which seems to be the isPresent state)

                    Palette palette = new Palette( buffer, wordTemplate, true );
                    short[] indexes = palette.getIndexes();

                    // Read NBT data
                    int needed = buffer.readLInt();
                    Int2ObjectMap<Pair<Integer, Byte>> chunkPalette = new Int2ObjectOpenHashMap<>( needed ); // Varint my ass

                    int index = 0;
                    NBTReaderNoBuffer reader = new NBTReaderNoBuffer( new InputStream() {
                        @Override
                        public int read() throws IOException {
                            return buffer.readByte();
                        }

                        @Override
                        public int available() throws IOException {
                            return buffer.getRemaining();
                        }
                    }, ByteOrder.LITTLE_ENDIAN );
                    while ( index < needed ) {
                        try {
                            NBTTagCompound compound = reader.parse();
                            int blockId = MaterialMagicNumbers.valueOfWithId( compound.getString( "name", "minecraft:air" ) );
                            if ( blockId == -1 ) {
                                LOGGER.error( "Unknown block {}", compound.getString( "name", "minecraft:air" ) );
                            }

                            byte blockData = compound.getShort( "val", (short) 0 ).byteValue();

                            chunkPalette.put( index++, new Pair<>( blockId, blockData ) );
                        } catch ( IOException e ) {
                            LOGGER.error( "Error in loading tile entities", e );
                            break;
                        }
                    }

                    for ( int j = 0; j < 16; ++j ) {
                        for ( int i = 0; i < 16; ++i ) {
                            for ( int k = 0; k < 16; ++k ) {
                                int y = ( sectionY << 4 ) + j;
                                short blockIndex = (short) ( k << 8 | i << 4 | j ); // j k i - k j i - i k j -

                                Pair<Integer, Byte> dataPair = chunkPalette.get( indexes[blockIndex] );
                                this.setBlock( k, y, i, sI, dataPair.getFirst() );
                                this.setData( k, y, i, sI, dataPair.getSecond() );
                            }
                        }
                    }
                }

                break;

            case 0:
                // Next 4096 bytes are block data
                byte[] blockData = new byte[4096];
                buffer.readBytes( blockData );

                // Next 2048 bytes are metadata
                byte[] metaData = new byte[2048];
                buffer.readBytes( metaData );
                HeapNibbleArray meta = new HeapNibbleArray( metaData );

                // In older versions of the chunk there are light values saved
                if ( this.chunkVersion < 4 ) {
                    // TODO: Get skylight data and check if its correct
                }

                for ( int j = 0; j < 16; ++j ) {
                    for ( int i = 0; i < 16; ++i ) {
                        for ( int k = 0; k < 16; ++k ) {
                            int y = ( sectionY << 4 ) + j;
                            short blockIndex = (short) ( k << 8 | i << 4 | j ); // j k i - k j i - i k j -
                            byte blockId = blockData[blockIndex];
                            this.setBlock( k, y, i, 0, blockId );

                            if ( meta.get( blockIndex ) != 0 ) {
                                this.setData( k, y, i, 0, meta.get( blockIndex ) );
                            }

                            switch ( blockId ) {
                                case 29:
                                case 33: // Piston head
                                    BlockPosition position = new BlockPosition( ( this.x << 4 ) + k, y, ( this.z << 4 ) + i );
                                    this.postProcessors.offer( new PistonPostProcessor( this.world, position ) );
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
        }
    }

    void loadTileEntities( byte[] tileEntityData ) {
        ByteArrayInputStream bais = new ByteArrayInputStream( tileEntityData );
        NBTReader nbtReader = new NBTReader( bais, ByteOrder.LITTLE_ENDIAN );
        while ( nbtReader.hasMoreToRead() ) {
            try {
                NBTTagCompound compound = nbtReader.parse();

                TileEntity tileEntity = TileEntities.construct( compound, this.world );
                if ( tileEntity != null ) {
                    this.addTileEntity( tileEntity );
                }
            } catch ( IOException e ) {
                LOGGER.error( "Error in loading tile entities", e );
                break;
            }
        }
    }

    void loadEntities( byte[] entityData ) {
        ByteArrayInputStream bais = new ByteArrayInputStream( entityData );
        NBTReader nbtReader = new NBTReader( bais, ByteOrder.LITTLE_ENDIAN );
        while ( nbtReader.hasMoreToRead() ) {
            try {
                NBTTagCompound compound = nbtReader.parse();
                Integer id = compound.getInteger( "id", 0 );
                Entity entity = this.world.getServer().getEntities().create( id & 0xFF );
                if ( entity != null ) {
                    entity.initFromNBT( compound );
                }
            } catch ( IOException e ) {
                LOGGER.error( "Error in loading entities", e );
                break;
            }
        }
    }

}
