package io.gomint.server.world;

import com.koloboke.collect.map.ShortObjMap;
import com.koloboke.collect.map.hash.HashShortObjMaps;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;
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

    @Getter private final ChunkAdapter chunk;
    @Getter private final int sectionY;

    private boolean isAllAir = true;

    private byte[] blocks = null;
    private NibbleArray data = null;
    private NibbleArray blockLight = new NibbleArray( 4096 );
    private NibbleArray skyLight = new NibbleArray( 4096 );

    private ShortObjMap<TileEntity> tileEntities = HashShortObjMaps.newMutableMap();
    private ShortObjMap<TemporaryStorage> temporaryStorages = HashShortObjMaps.newMutableMap();

    private int getIndex( int x, int y, int z ) {
        return ( x << 8 ) + ( z << 4 ) + y;
    }

    byte getBlock( int x, int y, int z ) {
        if ( this.isAllAir ) {
            return 0;
        }

        return this.blocks[getIndex( x, y, z )];
    }

    <T extends io.gomint.world.block.Block> T getBlockInstance( int x, int y, int z ) {
        int index = getIndex( x, y, z );

        int fullX = CoordinateUtils.getChunkMin( this.chunk.getX() ) + x;
        int fullY = CoordinateUtils.getChunkMin( this.sectionY ) + y;
        int fullZ = CoordinateUtils.getChunkMin( this.chunk.getZ() ) + z;

        if ( isAllAir ) {
            return (T) Blocks.get( 0, (byte) 0, this.skyLight.get( index ), this.blockLight.get( index ), null, new Location( this.chunk.world, fullX, fullY, fullZ ) );
        }

        return (T) Blocks.get( this.blocks[index] & 0xFF, this.data == null ? 0 : this.data.get( index ), this.skyLight.get( index ),
                this.blockLight.get( index ), this.tileEntities.get( (short) index ), new Location( this.chunk.world, fullX, fullY, fullZ ) );
    }

    Collection<TileEntity> getTileEntities() {
        List<TileEntity> tileEntities = new ArrayList<>();

        this.tileEntities.values().cursor().forEachForward( new Consumer<TileEntity>() {
            @Override
            public void accept( TileEntity tileEntity ) {
                tileEntities.add( tileEntity );
            }
        } );

        return tileEntities;
    }

    void addTileEntity( int x, int y, int z, TileEntity tileEntity ) {
        int index = getIndex( x, y, z );
        this.tileEntities.put( (short) index, tileEntity );
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
        int index = getIndex( x, y, z );

        if ( !this.isAllAir ) {
            if ( this.data == null ) {
                this.data = new NibbleArray( 4096 );
            }
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
        int index = getIndex( x, y, z );

        TemporaryStorage storage = this.temporaryStorages.get( (short) index );
        if ( storage == null ) {
            storage = new TemporaryStorage();
            this.temporaryStorages.put( (short) index, storage );
        }

        return storage;
    }

}
