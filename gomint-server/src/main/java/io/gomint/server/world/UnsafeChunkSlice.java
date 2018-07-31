/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.util.PerformanceHacks;
import io.gomint.server.util.performance.UnsafeAllocator;
import sun.misc.Unsafe;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UnsafeChunkSlice extends ChunkSlice {

    private static final Unsafe UNSAFE = (Unsafe) PerformanceHacks.getUnsafe();
    private long[] blockStorages = new long[2];

    public UnsafeChunkSlice( ChunkAdapter chunkAdapter, int sectionY ) {
        super( chunkAdapter, sectionY );
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

        long blockStorageAddress = this.blockStorages[layer];
        if ( blockStorageAddress == 0 ) {
            return 0;
        }

        return UNSAFE.getShort( blockStorageAddress + ( index << 1 ) );
    }

    @Override
    public void setBlockInternal( short index, int layer, int blockId ) {
        if ( blockId != 0 && this.blockStorages[layer] == 0 ) {
            this.blockStorages[layer] = UnsafeAllocator.allocate( 8192 ); // we store 4096 shorts
            UNSAFE.setMemory( this.blockStorages[layer], 8192L, (byte) 0 );
            this.isAllAir = false;
        }

        if ( this.blockStorages[layer] != 0 ) {
            UNSAFE.putShort( this.blockStorages[layer] + ( index << 1 ), (short) blockId );
        }
    }

    @Override
    protected int getAmountOfLayers() {
        return this.blockStorages[1] != 0 ? 2 : 1;
    }

    @Override
    protected void finalize() throws Throwable {
        for ( long blockStorage : blockStorages ) {
            if ( blockStorage != 0 ) {
                UnsafeAllocator.freeMemory( blockStorage );
            }
        }
    }

}
