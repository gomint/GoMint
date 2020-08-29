/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
    private final boolean releaseAfterSave;

    /**
     * Save the chunk into its on disk format
     *
     * @param chunk The chunk which should be saved
     * @param releaseAfterSave should this chunk be freed after saving?
     */
    AsyncChunkSaveTask( ChunkAdapter chunk, boolean releaseAfterSave ) {
        super( Type.SAVE );
        this.chunk = chunk;
        this.releaseAfterSave = releaseAfterSave;
    }

    /**
     * Get the chunk which should be saved
     *
     * @return chunk which should be saved
     */
    public ChunkAdapter getChunk() {
        return this.chunk;
    }

    /**
     * Should this chunk be freed after save
     *
     * @return true when it should be freed, false otherwise
     */
    public boolean isReleaseAfterSave() {
        return releaseAfterSave;
    }

}
