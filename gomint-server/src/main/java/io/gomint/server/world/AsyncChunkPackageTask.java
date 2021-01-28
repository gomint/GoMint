/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.async.Delegate2;


/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
class AsyncChunkPackageTask extends AsyncChunkTask {

    private int x;
    private int z;
    private Delegate2<Long, ChunkAdapter> callback;

    /**
     * This task should package the chunk into the PE packet format
     *
     * @param x        The X coordinate of the chunk
     * @param z        The Z coordinate of the chunk
     * @param callback The callback which is invoked when the chunk has been packed
     */
    AsyncChunkPackageTask( int x, int z, Delegate2<Long, ChunkAdapter> callback ) {
        super( Type.PACKAGE );
        this.x = x;
        this.z = z;
        this.callback = callback;
    }

    public int x() {
        return this.x;
    }

    public int z() {
        return this.z;
    }

    public Delegate2<Long, ChunkAdapter> callback() {
        return this.callback;
    }
}
