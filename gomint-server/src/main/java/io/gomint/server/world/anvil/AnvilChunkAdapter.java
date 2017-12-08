/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.Pair;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.NibbleArray;
import io.gomint.server.world.WorldLoadException;
import io.gomint.server.world.postprocessor.PistonPostProcessor;
import io.gomint.taglib.NBTStream;
import io.gomint.taglib.NBTTagCompound;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = true )
public class AnvilChunkAdapter extends ChunkAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger( AnvilChunkAdapter.class );
    private static final DataConverter CONVERTER = new DataConverter();

    private boolean converted;
    private boolean invalid;

    /**
     * Load a Chunk from a NBTTagCompound. This is used when loaded from a Regionfile.
     *
     * @param worldAdapter which loaded this chunk
     * @param x            position of chunk
     * @param z            position of chunk
     */
    public AnvilChunkAdapter( AnvilWorldAdapter worldAdapter, int x, int z ) {
        super( worldAdapter, x, z );
        this.lastSavedTimestamp = worldAdapter.getServer().getCurrentTickTime();
        this.loadedTime = this.lastSavedTimestamp;
        this.converted = worldAdapter.isOverrideConverter();
    }

    // ==================================== I/O ==================================== //

    /**
     * Writes the chunk's raw NBT data to the given output stream.
     *
     * @param out The output stream to write the chunk data to.
     * @throws IOException Thrown in case the chunk could not be stored
     */
    void saveToNBT( OutputStream out ) throws IOException {
        LOGGER.debug( "Writing Anvil chunk {}", this );

        NBTTagCompound chunk = new NBTTagCompound( "" );

        NBTTagCompound level = new NBTTagCompound( "Level" );
        level.addValue( "LightPopulated", (byte) 1 );
        level.addValue( "TerrainPopulated", (byte) 1 );
        level.addValue( "V", (byte) 1 );
        level.addValue( "xPos", this.x );
        level.addValue( "zPos", this.z );
        level.addValue( "InhabitedTime", this.inhabitedTime );
        level.addValue( "LastUpdate", 0L );
        level.addValue( "Biomes", this.biomes );
        level.addValue( "GoMintConverted", (byte) 1 );

        List<NBTTagCompound> sections = new ArrayList<>( 8 );

        for ( int sectionY = 0; sectionY < 16; ++sectionY ) {
            byte[] blocks = new byte[4096];
            NibbleArray data = new NibbleArray( (short) 4096 );
            int baseIndex = sectionY * 16;

            for ( int y = baseIndex; y < baseIndex + 16; ++y ) {
                for ( int x = 0; x < 16; ++x ) {
                    for ( int z = 0; z < 16; ++z ) {
                        short blockIndex = (short) ( ( y - baseIndex ) << 8 | z << 4 | x );

                        byte blockId = this.getBlock( x, y, z );
                        byte blockData = this.getData( x, y, z );

                        blocks[blockIndex] = blockId;
                        data.set( blockIndex, blockData );
                    }
                }
            }

            NBTTagCompound section = new NBTTagCompound( "" );
            section.addValue( "Y", (byte) sectionY );
            section.addValue( "Blocks", blocks );
            section.addValue( "Data", data.raw() );
            sections.add( section );
        }

        level.addValue( "Sections", sections );
        level.addValue( "Entities", new ArrayList( 0 ) );

        List<NBTTagCompound> tileEntityCompounds = new ArrayList<>();
        for ( TileEntity tileEntity : this.getTileEntities() ) {
            NBTTagCompound compound = new NBTTagCompound( "" );
            tileEntity.toCompound( compound );
            tileEntityCompounds.add( compound );
        }

        level.addValue( "TileEntities", tileEntityCompounds );

        chunk.addChild( level );
        chunk.writeTo( out, false, ByteOrder.BIG_ENDIAN );
    }

    /**
     * Loads the chunk from the specified NBTTagCompound
     *
     * @param nbtStream The stream which loads the chunk
     */
    // CHECKSTYLE:OFF
    void loadFromNBT( NBTStream nbtStream ) throws WorldLoadException {
        // Fill in default values
        this.biomes = new byte[256];
        Arrays.fill( this.biomes, (byte) -1 );

        // Wait until the nbt stream sends some data
        final int[] oldSectionIndex = new int[]{ -1 };
        final SectionCache[] currentSectionCache = new SectionCache[]{ new SectionCache() };
        final List<SectionCache> sections = new ArrayList<>();
        final List<NBTTagCompound> tileEntityHolders = new ArrayList<>();

        nbtStream.addListener( ( path, object ) -> {
            switch ( path ) {
                case ".Level.xPos":
                    int xPos = (int) object;
                    if ( AnvilChunkAdapter.this.x != xPos ) {
                        AnvilChunkAdapter.this.invalid = true;
                    }

                    break;
                case ".Level.zPos":
                    int zPos = (int) object;
                    if ( AnvilChunkAdapter.this.z != zPos ) {
                        AnvilChunkAdapter.this.invalid = true;
                    }

                    break;
                case ".Level.Biomes":
                    AnvilChunkAdapter.this.biomes = (byte[]) object;
                    break;
                case ".Level.InhabitedTime":
                    AnvilChunkAdapter.this.inhabitedTime = (long) object;
                    break;
                case ".Level.GoMintConverted":
                    AnvilChunkAdapter.this.converted = true;
                    break;
                default:
                    if ( path.startsWith( ".Level.Sections" ) ) {
                        // Parse the index
                        String[] split = path.split( "\\." );
                        int sectionIndex = Integer.parseInt( split[3] );

                        // Check if we completed a chunk
                        if ( oldSectionIndex[0] != -1 && oldSectionIndex[0] != sectionIndex ) {
                            // Load and convert this section
                            sections.add( currentSectionCache[0] );

                            // Reset the cache
                            currentSectionCache[0] = new SectionCache();
                        }

                        oldSectionIndex[0] = sectionIndex;

                        // Check what we have got from the chunk
                        switch ( split[4] ) {
                            case "Y":
                                currentSectionCache[0].setSectionY( (byte) object << 4 );
                                break;
                            case "Blocks":
                                currentSectionCache[0].setBlocks( (byte[]) object );
                                break;
                            case "Add":
                                currentSectionCache[0].setAdd( new NibbleArray( (byte[]) object ) );
                                break;
                            case "Data":
                                currentSectionCache[0].setData( new NibbleArray( (byte[]) object ) );
                                break;
                            default:
                                break;
                        }
                    } else if ( path.startsWith( ".Level.TileEntities" ) ) {
                        String[] split = path.split( "\\." );
                        int index = Integer.parseInt( split[3] );

                        if ( tileEntityHolders.size() == index ) {
                            tileEntityHolders.add( new NBTTagCompound( null ) );
                        }

                        NBTTagCompound entityHolder = tileEntityHolders.get( index );
                        String key;

                        if ( split.length > 5 ) {
                            // Restore missing maps and lists
                            for ( int i = 4; i < split.length - 1; i++ ) {
                                // Peek one to terminate if this is a map or a list
                                try {
                                    int idx = Integer.parseInt( split[i + 1] );

                                    // Get or create list
                                    List list = entityHolder.getList( split[i], true );
                                    if ( list.size() == idx ) {
                                        Object obj = null;

                                        if ( split.length > i + 1 ) {
                                            // Need another list of nbt compounds
                                            obj = entityHolder = new NBTTagCompound( split[i + 2] );
                                        }

                                        if ( obj != null ) {
                                            list.add( obj );
                                        }
                                    }
                                } catch ( Exception ignored ) {
                                    try {
                                        Integer.parseInt( split[i] );
                                    } catch ( Exception ignored1 ) {
                                        NBTTagCompound temp = new NBTTagCompound( split[i] );
                                        entityHolder.addValue( split[i], temp );
                                        entityHolder = temp;
                                    }
                                }
                            }

                            key = split[split.length - 1];
                        } else {
                            key = split[4];
                        }

                        Class clazz = object.getClass();
                        if ( clazz.equals( Integer.class ) ) {
                            entityHolder.addValue( key, (int) object );
                        } else if ( clazz.equals( String.class ) ) {
                            entityHolder.addValue( key, (String) object );
                        } else if ( clazz.equals( Byte.class ) ) {
                            entityHolder.addValue( key, (Byte) object );
                        } else if ( clazz.equals( Short.class ) ) {
                            entityHolder.addValue( key, (Short) object );
                        } else {
                            LOGGER.warn( "Unknown tile entity data class {} for key {}", clazz, key );
                        }
                    }
            }
        } );

        // Start parsing the nbt tag
        try {
            nbtStream.parse();
        } catch ( Exception e ) {
            LOGGER.error( "Error whilst parsing chunk nbt: ", e );
        }

        if ( this.invalid ) {
            throw new WorldLoadException( "Position stored in chunk does not match region file offset position" );
        }

        if ( currentSectionCache[0].getBlocks() != null ) {
            sections.add( currentSectionCache[0] );
        }

        // Load sections
        for ( SectionCache section : sections ) {
            this.loadSection( section );
        }

        sections.clear();

        // Load tile entities
        if ( !tileEntityHolders.isEmpty() ) {
            for ( NBTTagCompound tileEntity : tileEntityHolders ) {
                String id = tileEntity.getString( "id", "" );
                switch ( id ) {
                    case "Sign":
                        TileEntityConverter.cleanSignText( tileEntity, "Text1" );
                        TileEntityConverter.cleanSignText( tileEntity, "Text2" );
                        TileEntityConverter.cleanSignText( tileEntity, "Text3" );
                        TileEntityConverter.cleanSignText( tileEntity, "Text4" );
                        break;

                    case "Skull":
                        // Remove the owner or extra data
                        if ( tileEntity.containsKey( "Owner" ) ) {
                            tileEntity.remove( "Owner" );
                        }
                        break;

                    case "RecordPlayer":
                        tileEntity.addValue( "id", "Music" );
                        tileEntity.addValue( "note", (byte) 0 );

                        if ( tileEntity.containsKey( "Record" ) ) {
                            tileEntity.remove( "Record" );
                        }

                        if ( tileEntity.containsKey( "RecordItem" ) ) {
                            tileEntity.remove( "RecordItem" );
                        }

                        break;

                    case "Banner":
                    case "Airportal":
                        continue;

                    default:
                        break;

                }

                this.addTileEntity( tileEntity );
            }
        }

        this.calculateHeightmap();
    }
    // CHECKSTYLE:ON

    /**
     * Loads a chunk section from its raw NBT data.
     *
     * @param section The section to be loaded
     */
    private void loadSection( SectionCache section ) {
        int sectionY = section.getSectionY();
        byte[] blocks = section.getBlocks();
        NibbleArray add = section.getAdd();
        NibbleArray data = section.getData();

        if ( blocks == null || data == null ) {
            throw new IllegalArgumentException( "Corrupt chunk: Section is missing obligatory compounds" );
        }

        for ( int j = 0; j < 16; ++j ) {
            for ( int i = 0; i < 16; ++i ) {
                for ( int k = 0; k < 16; ++k ) {
                    int y = sectionY + j;
                    short blockIndex = (short) ( j << 8 | k << 4 | i );

                    int blockId = ( ( add != null ? add.get( blockIndex ) << 8 : 0 ) | blocks[blockIndex] ) & 0xFF;
                    byte blockData = data.get( blockIndex );

                    if ( !converted ) {
                        Pair<Integer, Byte> convertedData = CONVERTER.convert( blockId, blockData );
                        if ( convertedData != null ) {
                            blockId = convertedData.getFirst();
                            blockData = convertedData.getSecond();
                        }

                        // Block data converter
                        if ( blockId == 3 && blockData == 1 ) {
                            blockId = 198;
                            blockData = 0;
                        } else if ( blockId == 3 && blockData == 2 ) {
                            blockId = 243;
                            blockData = 0;
                        }

                        // Fix water & lava at the bottom of a chunk
                        if ( y == 0 && ( blockId == 8 || blockId == 9 || blockId == 10 || blockId == 11 ) ) {
                            blockId = 7;
                            blockData = 0;
                        }
                    }

                    this.setBlock( i, y, k, blockId );

                    if ( blockData != 0 ) {
                        this.setData( i, y, k, blockData );
                    }

                    switch ( blockId ) {
                        case 29:
                        case 33: // Piston head
                            BlockPosition position = new BlockPosition( ( this.x << 4 ) + i, y, ( this.z << 4 ) + k );
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
