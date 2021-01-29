/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class AsyncChunkSaveTask extends AsyncChunkTask {

    private final ChunkAdapter chunk;

    /**
     * Save the chunk into its on disk format
     *
     * @param chunk The chunk which should be saved
     */
    AsyncChunkSaveTask( ChunkAdapter chunk ) {
        super( Type.SAVE );
        this.chunk = chunk;
    }

    /**
     * Get the chunk which should be saved
     *
     * @return chunk which should be saved
     */
    public ChunkAdapter chunk() {
        return this.chunk;
    }

}
