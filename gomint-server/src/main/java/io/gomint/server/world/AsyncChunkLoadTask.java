/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.async.Future;
import lombok.Setter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class AsyncChunkLoadTask extends AsyncChunkTask {

    private int x;
    private int z;
    @Setter private boolean generate;
    @Setter private Future<ChunkAdapter> future;

    /**
     * Construct a new loading task
     *
     * @param x        The X coordinate of the chunk
     * @param z        The Z coordinate of the chunk
     * @param generate Is it allowed to generate the chunk if its missing?
     * @param future   A future which gets resolved when the chunk has been loaded
     */
    AsyncChunkLoadTask(int x, int z, boolean generate, Future<ChunkAdapter> future) {
        super(Type.LOAD);
        this.x = x;
        this.z = z;
        this.generate = generate;
        this.future = future;
    }

    /**
     * Get the X coordinate of the chunk
     *
     * @return x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the Z coordinate of the chunk
     *
     * @return z coordinate
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Is this chunk allowed to generate when not existing
     *
     * @return allowed to generate
     */
    boolean isGenerate() {
        return this.generate;
    }

    /**
     * The callback which should be invoked when the task has been completed
     *
     * @return the callback which should be invoked on completion
     */
    Future<ChunkAdapter> getFuture() {
        return this.future;
    }

}
