package io.gomint.server.world;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.collection.ChunkSliceIndexMap;
import io.gomint.server.world.block.Blocks;
import io.gomint.server.world.storage.TemporaryStorage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
class ChunkSlice {

    @Getter
    private final ChunkAdapter chunk;
    @Getter
    private final int sectionY;

    private boolean isAllAir = true;

    private byte[] blocks = null;
    private NibbleArray data = null;
    private NibbleArray blockLight = new NibbleArray( (short) 4096 );
    private NibbleArray skyLight = new NibbleArray( (short) 4096 );

    private ChunkSliceIndexMap<TileEntity> tileEntities = ChunkSliceIndexMap.withExpectedSize( 20 );
    private ChunkSliceIndexMap<TemporaryStorage> temporaryStorages = ChunkSliceIndexMap.withExpectedSize( 20 );

    private short getIndex( int x, int y, int z ) {
        return (short) ( ( x << 8 ) + ( z << 4 ) + y );
    }

    /**
     * Get the ID of the specific block in question
     *
     * @param x coordinate in this slice (capped to 16)
     * @param y coordinate in this slice (capped to 16)
     * @param z coordinate in this slice (capped to 16)
     * @return id of the block
     */
    byte getBlock( int x, int y, int z ) {
        return this.getBlockInternal( getIndex( x, y, z ) );
    }

    /**
     * Get a block by its index
     *
     * @param index which should be looked up
     * @return block id of the index
     */
    byte getBlockInternal( short index ) {
        if ( this.isAllAir ) {
            return 0;
        }

        return this.blocks[index];
    }

    <T extends io.gomint.world.block.Block> T getBlockInstance( int x, int y, int z ) {
        short index = getIndex( x, y, z );

        int fullX = CoordinateUtils.getChunkMin( this.chunk.getX() ) + x;
        int fullY = CoordinateUtils.getChunkMin( this.sectionY ) + y;
        int fullZ = CoordinateUtils.getChunkMin( this.chunk.getZ() ) + z;

        if ( isAllAir ) {
            return (T) Blocks.get( 0, (byte) 0, this.skyLight.get( index ), this.blockLight.get( index ), null, new Location( this.chunk.world, fullX, fullY, fullZ ) );
        }

        return (T) Blocks.get( this.blocks[index] & 0xFF, this.data == null ? 0 : this.data.get( index ), this.skyLight.get( index ),
            this.blockLight.get( index ), this.tileEntities.get( index ), new Location( this.chunk.world, fullX, fullY, fullZ ) );
    }

    Collection<TileEntity> getTileEntities() {
        List<TileEntity> tileEntities = new ArrayList<>();

        this.tileEntities.values().forEach( new Consumer<TileEntity>() {
            @Override
            public void accept( TileEntity tileEntity ) {
                tileEntities.add( tileEntity );
            }
        } );

        return tileEntities;
    }

    void addTileEntity( int x, int y, int z, TileEntity tileEntity ) {
        int index = getIndex( x, y, z );
        this.tileEntities.justPut( (short) index, tileEntity );
    }

    void setBlock( int x, int y, int z, byte blockId ) {
        int index = getIndex( x, y, z );

        if ( blockId != 0 ) {
            if ( this.blocks == null ) {
                this.blocks = new byte[4096];
                this.isAllAir = false;
            }
        }

        if ( this.blocks != null ) {
            this.blocks[index] = blockId;
        }
    }

    void setData( int x, int y, int z, byte data ) {
        short index = getIndex( x, y, z );

        if ( !this.isAllAir ) {
            if ( this.data == null ) {
                this.data = new NibbleArray( (short) 4096 );
            }
        }

        // All air and we want to set block data? How about no!
        if ( this.data == null ) {
            return;
        }

        this.data.set( index, data );
    }

    byte getData( int x, int y, int z ) {
        if ( this.data == null ) {
            return 0;
        }

        return this.data.get( getIndex( x, y, z ) );
    }

    boolean isAllAir() {
        return this.isAllAir;
    }

    byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write( this.blocks == null ? new byte[4096] : this.blocks );
            baos.write( this.data == null ? new byte[2048] : this.data.raw() );
        } catch ( Exception ignored ) {

        }

        return baos.toByteArray();
    }

    public TemporaryStorage getTemporaryStorage( int x, int y, int z ) {
        short index = getIndex( x, y, z );

        TemporaryStorage storage = this.temporaryStorages.get( index );
        if ( storage == null ) {
            storage = new TemporaryStorage();
            this.temporaryStorages.justPut( index, storage );
        }

        return storage;
    }

    public void resetTemporaryStorage( int x, int y, int z ) {
        short index = getIndex( x, y, z );
        this.temporaryStorages.justRemove( index );
    }

}
