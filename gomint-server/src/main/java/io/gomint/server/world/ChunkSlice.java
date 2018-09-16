package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.Palette;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.world.block.Block;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ChunkSlice {

    protected static final short AIR_RUNTIME_ID = (short) BlockRuntimeIDs.from( "minecraft:air", (short) 0 );

    @Getter private final ChunkAdapter chunk;
    @Getter private final int sectionY;

    // Cache
    private int shiftedMinX;
    private int shiftedMinY;
    private int shiftedMinZ;

    protected boolean isAllAir = true;

    private short[][] blocks = new short[2][]; // MC currently supports two layers, we init them as we need

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
    String getBlock( int x, int y, int z, int layer ) {
        return this.getBlock( layer, getIndex( x, y, z ) );
    }

    String getBlock( int layer, int index ) {
        int runtimeId = this.getRuntimeID( layer, index );
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier( runtimeId );
        return identifier.getBlockId();
    }

    /**
     * Get a block by its index
     *
     * @param x     coordinate in this slice (capped to 16)
     * @param y     coordinate in this slice (capped to 16)
     * @param z     coordinate in this slice (capped to 16)
     * @param layer on which the block is
     * @return block id of the index
     */
    int getRuntimeID( int x, int y, int z, int layer ) {
        return this.getRuntimeID( layer, getIndex( x, y, z ) );
    }

    protected int getRuntimeID( int layer, int index ) {
        if ( this.isAllAir ) {
            return AIR_RUNTIME_ID;
        }

        short[] blockStorage = this.blocks[layer];
        if ( blockStorage == null ) {
            return AIR_RUNTIME_ID;
        }

        return blockStorage[index];
    }

    <T extends io.gomint.world.block.Block> T getBlockInstance( int x, int y, int z, int layer ) {
        short index = getIndex( x, y, z );

        Location blockLocation = this.getBlockLocation( x, y, z );

        int runtimeID = this.getRuntimeID( layer, index );
        if ( this.isAllAir || runtimeID == AIR_RUNTIME_ID ) {
            return this.getAirBlockInstance( blockLocation );
        }

        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier( runtimeID );
        return (T) this.chunk.getWorld().getServer().getBlocks().get( identifier.getBlockId(), identifier.getData(), this.skyLight.get( index ),
            this.blockLight.get( index ), this.tileEntities.get( index ), blockLocation, layer );
    }

    private <T extends Block> T getAirBlockInstance( Location location ) {
        return (T) this.chunk.getWorld().getServer().getBlocks().get( "minecraft:air", (short) 0, (byte) 15, (byte) 15, null, location, 0 );
    }

    private Location getBlockLocation( int x, int y, int z ) {
        return new Location( this.chunk.world, this.shiftedMinX + x, this.shiftedMinY + y, this.shiftedMinZ + z );
    }

    void removeTileEntity( int x, int y, int z ) {
        this.removeTileEntityInternal( getIndex( x, y, z ) );
    }

    private void removeTileEntityInternal( short index ) {
        this.tileEntities.remove( index );
    }

    void addTileEntity( int x, int y, int z, TileEntity tileEntity ) {
        this.addTileEntityInternal( getIndex( x, y, z ), tileEntity );
    }

    public void addTileEntityInternal( short index, TileEntity tileEntity ) {
        this.tileEntities.put( index, tileEntity );
    }

    public void setBlock( int x, int y, int z, int layer, String blockId ) {
        short index = getIndex( x, y, z );
        int runtimeID = this.getRuntimeID( layer, index );
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier( runtimeID );
        this.setRuntimeIdInternal( index, layer, BlockRuntimeIDs.from( blockId, identifier.getData() ) );
    }

    void setBlock( int x, int y, int z, int layer, BlockIdentifier blockIdentifier ) {
        short index = getIndex( x, y, z );
        int runtimeId = BlockRuntimeIDs.from( blockIdentifier.getBlockId(), blockIdentifier.getData() );
        this.setRuntimeIdInternal( index, layer, runtimeId );
    }

    public void setData( int x, int y, int z, int layer, short data ) {
        short index = getIndex( x, y, z );
        int runtimeID = this.getRuntimeID( layer, index );
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier( runtimeID );
        this.setRuntimeIdInternal( index, layer, BlockRuntimeIDs.from( identifier.getBlockId(), data ) );
    }

    public void setRuntimeIdInternal( short index, int layer, int runtimeID ) {
        if ( runtimeID != AIR_RUNTIME_ID && this.blocks[layer] == null ) {
            this.blocks[layer] = new short[4096]; // Defaults to all 0
            for ( int i = 0; i < this.blocks[layer].length; i++ ) {
                this.blocks[layer][i] = AIR_RUNTIME_ID;
            }
            this.isAllAir = false;
        }

        if ( this.blocks[layer] != null ) {
            this.blocks[layer][index] = (short) runtimeID;
        }
    }

    boolean isAllAir() {
        return this.isAllAir;
    }

    public int getAmountOfLayers() {
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

        for ( int layer = 0; layer < amountOfLayers; layer++ ) {
            // Count how many unique blocks we have in this chunk
            int[] indexIDs = new int[4096];
            LongList indexList = new LongArrayList();
            IntList runtimeIndex = new IntArrayList();

            int foundIndex = 0;

            int lastRuntimeID = -1;

            for ( short blockIndex = 0; blockIndex < indexIDs.length; blockIndex++ ) {
                int runtimeID = this.getRuntimeID( layer, blockIndex );

                if ( runtimeID != lastRuntimeID ) {
                    foundIndex = indexList.indexOf( runtimeID );
                    if ( foundIndex == -1 ) {
                        runtimeIndex.add( runtimeID );
                        indexList.add( runtimeID );
                        foundIndex = indexList.size() - 1;
                    }

                    lastRuntimeID = runtimeID;
                }

                indexIDs[blockIndex] = foundIndex;
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

    public List<BlockIdentifier> getBlocks( int layer ) {
        List<BlockIdentifier> blocks = new ArrayList<>( 4096 );

        for ( int i = 0; i < 4096; i++ ) {
            int runtime = this.getRuntimeID( layer, i );
            blocks.add( BlockRuntimeIDs.toBlockIdentifier( runtime ) );
        }

        return blocks;
    }

}
