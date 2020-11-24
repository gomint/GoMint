/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.async.Delegate;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class AsyncChunkLoadTask extends AsyncChunkTask {

    private int x;
    private int z;
    private boolean generate;
    private Delegate<ChunkAdapter> callback;

    /**
     * Construct a new loading task
     *
     * @param x        The X coordinate of the chunk
     * @param z        The Z coordinate of the chunk
     * @param generate Is it allowed to generate the chunk if its missing?
     * @param callback A delegate which is called when the task has been completed
     */
    AsyncChunkLoadTask( int x, int z, boolean generate, Delegate<ChunkAdapter> callback ) {
        super( Type.LOAD );
        this.x = x;
        this.z = z;
        this.generate = generate;
        this.callback = callback;
    }

    /**
     * Get the X coordinate of the chunk
     *
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Z coordinate of the chunk
     *
     * @return z coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Is this chunk allowed to generate when not existing
     *
     * @return allowed to generate
     */
    boolean isGenerate() {
        return generate;
    }

    /**
     * The callback which should be invoked when the task has been completed
     *
     * @return the callback which should be invoked on completion
     */
    Delegate<ChunkAdapter> getCallback() {
        return callback;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public void setCallback(Delegate<ChunkAdapter> callback) {
        this.callback = callback;
    }
}
