package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.Palette;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.world.block.Block;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import lombok.Getter;

import java.util.function.IntConsumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ChunkSlice {

    @Getter
    private final ChunkAdapter chunk;
    @Getter
    private final int sectionY;

    // Cache
    private int shiftedMinX;
    private int shiftedMinY;
    private int shiftedMinZ;

    protected boolean isAllAir = true;

    private short[][] blocks = new short[2][]; // MC currently supports two layers, we init them as we need
    private NibbleArray[] data = new NibbleArray[2]; // MC currently supports two layers, we init them as we need

    private NibbleArray blockLight = NibbleArray.create( (short) 4096 );
    private NibbleArray skyLight = NibbleArray.create( (short) 4096 );

    @Getter
    private Short2ObjectOpenHashMap<TileEntity> tileEntities = new Short2ObjectOpenHashMap<>();
    private Short2ObjectOpenHashMap[] temporaryStorages = new Short2ObjectOpenHashMap[2];   // MC currently supports two layers, we init them as we need

    public ChunkSlice( ChunkAdapter chunkAdapter, int sectionY ) {
        this.chunk = chunkAdapter;
        this.sectionY = sectionY;

        // Calc shifted values for inline getter
        this.shiftedMinX = this.chunk.x << 4;
        this.shiftedMinY = this.sectionY << 4;
        this.shiftedMinZ = this.chunk.z << 4;
    }

    private short getIndex( int x, int y, int z ) {
        return (short) ( ( x << 8 ) + ( z << 4 ) + y );
    }

    /**
     * Get the ID of the specific block in question
     *
     * @param x     coordinate in this slice (capped to 16)
     * @param y     coordinate in this slice (capped to 16)
     * @param z     coordinate in this slice (capped to 16)
     * @param layer on which the block is
     * @return id of the block
     */
    int getBlock( int x, int y, int z, int layer ) {
        return this.getBlockInternal( layer, getIndex( x, y, z ) );
    }

    /**
     * Get a block by its index
     *
     * @param layer on which the block is
     * @param index which should be looked up
     * @return block id of the index
     */
    public int getBlockInternal( int layer, int index ) {
        if ( this.isAllAir ) {
            return 0;
        }

        short[] blockStorage = this.blocks[layer];
        if ( blockStorage == null ) {
            return 0;
        }

        return blockStorage[index];
    }

    <T extends io.gomint.world.block.Block> T getBlockInstance( int x, int y, int z, int layer ) {
        short index = getIndex( x, y, z );

        Location blockLocation = this.getBlockLocation( x, y, z );
        NibbleArray dataStorage = this.data[layer];

        int blockId = this.getBlockInternal( layer, index );
        if ( this.isAllAir || blockId == 0 ) {
            return this.getAirBlockInstance( blockLocation );
        }

        return (T) this.chunk.getWorld().getServer().getBlocks().get( blockId, dataStorage == null ? 0 : dataStorage.get( index ), this.skyLight.get( index ),
            this.blockLight.get( index ), this.tileEntities.get( index ), blockLocation, layer );
    }

    private <T extends Block> T getAirBlockInstance( Location location ) {
        return (T) this.chunk.getWorld().getServer().getBlocks().get( 0, (byte) 0, (byte) 15, (byte) 15, null, location, 0 );
    }

    private Location getBlockLocation( int x, int y, int z ) {
        return new Location( this.chunk.world, this.shiftedMinX + x, this.shiftedMinY + y, this.shiftedMinZ + z );
    }

    void addTileEntity( int x, int y, int z, TileEntity tileEntity ) {
        this.addTileEntityInternal( getIndex( x, y, z ), tileEntity );
    }

    public void addTileEntityInternal( short index, TileEntity tileEntity ) {
        this.tileEntities.put( index, tileEntity );
    }

    void setBlock( int x, int y, int z, int layer, int blockId ) {
        short index = getIndex( x, y, z );
        this.setBlockInternal( index, layer, blockId );
    }

    public void setBlockInternal( short index, int layer, int blockId ) {
        if ( blockId != 0 && this.blocks[layer] == null ) {
            this.blocks[layer] = new short[4096];
            this.isAllAir = false;
        }

        if ( this.blocks[layer] != null ) {
            this.blocks[layer][index] = (short) blockId;
        }
    }

    void setData( int x, int y, int z, int layer, byte data ) {
        short index = getIndex( x, y, z );
        this.setDataInternal( index, layer, data );
    }

    public void setDataInternal( short index, int layer, byte data ) {
        // Check if we need to set new nibble array
        if ( !this.isAllAir && this.data[layer] == null ) {
            this.data[layer] = NibbleArray.create( (short) 4096 );
        }

        // All air and we want to set block data? How about no!
        if ( this.data[layer] == null ) {
            return;
        }

        this.data[layer].set( index, data );
    }

    byte getData( int x, int y, int z, int layer ) {
        return this.getDataInternal( layer, getIndex( x, y, z ) );
    }

    public byte getDataInternal( int layer, short index ) {
        if ( this.data[layer] == null ) {
            return 0;
        }

        return this.data[layer].get( index );
    }

    boolean isAllAir() {
        return this.isAllAir;
    }

    /*byte[] getBytes( int protocolID ) {
        // Check how many layers we have
        int amountOfLayers = this.getAmountOfLayers();

        PacketBuffer buffer = new PacketBuffer( Short.MAX_VALUE );
        buffer.writeByte( (byte) amountOfLayers );

        for ( int i = 0; i < amountOfLayers; i++ ) {
            // Count how many unique blocks we have in this chunk
            int index = 0;
            Long2IntMap ids = new Long2IntArrayMap();
            Long2IntMap runtimeIndex = new Long2IntArrayMap();
            ids.defaultReturnValue( -1 );
            int[] indexIDs = new int[4096];

            for ( int x = 0; x < 16; x++ ) {
                for ( int z = 0; z < 16; z++ ) {
                    for ( int y = 0; y < 16; y++ ) {
                        short blockIndex = (short) ( ( x << 8 ) + ( z << 4 ) + y );
                        int blockId = this.getBlockInternal( i, blockIndex );
                        byte blockData = this.data[i] == null ? 0 : this.data[i].get( blockIndex );

                        long hashId = ( (long) blockId ) << 32 | ( blockData & 0xffffffffL );
                        int foundIndex = ids.get( hashId );
                        if ( foundIndex == -1 ) {
                            int runtimeId = BlockRuntimeIDs.fromLegacy( blockId, blockData, protocolID );
                            runtimeIndex.put( index, runtimeId );
                            ids.put( hashId, index );
                            foundIndex = index;
                            index++;
                        }

                        indexIDs[blockIndex] = foundIndex;
                    }
                }
            }

            // Get correct wordsize
            int value = ids.size();
            int numberOfBits = MathUtils.fastFloor( log2( value ) ) + 1;

            // Prepare palette
            int amountOfBlocks = MathUtils.fastFloor( 32f / (float) numberOfBits );

            Palette palette = new Palette( buffer, amountOfBlocks, false );

            byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
            buffer.writeByte( paletteWord );

            for ( Integer id : indexIDs ) {
                palette.addIndex( id );
            }

            palette.finish();

            // Write runtimeIDs
            buffer.writeSignedVarInt( ids.size() );

            Long2IntMap.FastEntrySet entrySet = (Long2IntMap.FastEntrySet) runtimeIndex.long2IntEntrySet();
            ObjectIterator<Long2IntMap.Entry> iterator = entrySet.fastIterator();
            while ( iterator.hasNext() ) {
                buffer.writeSignedVarInt( iterator.next().getIntValue() );
            }
        }

        // Copy result
        byte[] outputData = new byte[buffer.getPosition()];
        System.arraycopy( buffer.getBuffer(), buffer.getBufferOffset(), outputData, 0, buffer.getPosition() );
        return outputData;
    }*/

    protected int getAmountOfLayers() {
        return this.blocks[1] != null ? 2 : 1;
    }

    private int log2( int n ) {
        if ( n <= 0 ) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros( n );
    }

    public TemporaryStorage getTemporaryStorage( int x, int y, int z, int layer ) {
        short index = getIndex( x, y, z );

        // Select correct layer
        Short2ObjectOpenHashMap<TemporaryStorage> storage = this.temporaryStorages[layer];
        if ( storage == null ) {
            storage = new Short2ObjectOpenHashMap<>();
            this.temporaryStorages[layer] = storage;
        }

        TemporaryStorage blockStorage = storage.get( index );
        if ( blockStorage == null ) {
            blockStorage = new TemporaryStorage();
            storage.put( index, blockStorage );
        }

        return blockStorage;
    }

    public void resetTemporaryStorage( int x, int y, int z, int layer ) {
        Short2ObjectOpenHashMap<TemporaryStorage> storage = this.temporaryStorages[layer];
        if ( storage == null ) {
            return;
        }

        short index = getIndex( x, y, z );
        storage.remove( index );
    }

    public TileEntity getTileInternal( short i ) {
        return this.tileEntities.get( i );
    }

    public void writeToNetwork( PacketBuffer buffer ) {
        buffer.writeByte( (byte) 8 );

        // Check how many layers we have
        int amountOfLayers = this.getAmountOfLayers();
        buffer.writeByte( (byte) amountOfLayers );

        for ( int i = 0; i < amountOfLayers; i++ ) {
            // Count how many unique blocks we have in this chunk
            int[] indexIDs = new int[4096];
            LongList indexList = new LongArrayList();
            IntList runtimeIndex = new IntArrayList();

            int foundIndex = 0;
            long lastHash = -1;

            for ( int x = 0; x < 16; x++ ) {
                for ( int z = 0; z < 16; z++ ) {
                    for ( int y = 0; y < 16; y++ ) {
                        short blockIndex = (short) ( ( x << 8 ) + ( z << 4 ) + y );
                        int blockId = this.getBlockInternal( i, blockIndex );
                        byte blockData = blockId == 0 || this.data[i] == null ? 0 : this.data[i].get( blockIndex );

                        long hashId = ( (long) blockId ) << 32 | ( blockData & 0xffffffffL );
                        if ( hashId != lastHash ) {
                            foundIndex = indexList.indexOf( hashId );
                            if ( foundIndex == -1 ) {
                                int runtimeId = BlockRuntimeIDs.fromLegacy( blockId, blockData );
                                runtimeIndex.add( runtimeId );
                                indexList.add( hashId );
                                foundIndex = indexList.size() - 1;
                            }
                        }

                        indexIDs[blockIndex] = foundIndex;
                    }
                }
            }

            // Get correct wordsize
            int value = indexList.size();
            int numberOfBits = MathUtils.fastFloor( log2( value ) ) + 1;

            // Prepare palette
            int amountOfBlocks = MathUtils.fastFloor( 32f / (float) numberOfBits );

            Palette palette = new Palette( buffer, amountOfBlocks, false );

            byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
            buffer.writeByte( paletteWord );
            palette.addIndexIDs( indexIDs );
            palette.finish();

            // Write runtimeIDs
            buffer.writeSignedVarInt( indexList.size() );
            runtimeIndex.forEach( (IntConsumer) buffer::writeSignedVarInt );
        }
    }

}
