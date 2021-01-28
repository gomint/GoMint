/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla.chunk;

import io.gomint.math.BlockPosition;
import io.gomint.server.async.Future;
import io.gomint.server.world.ChunkAdapter;

import java.util.Objects;

public class ChunkRequest {

    private int x;
    private int z;
    private Future<ChunkAdapter> future;

    public ChunkRequest(int x, int z, Future<ChunkAdapter> future) {
        this.x = x;
        this.z = z;
        this.future = future;
    }

    public int x() {
        return this.x;
    }

    public int z() {
        return this.z;
    }

    public Future<ChunkAdapter> future() {
        return this.future;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkRequest that = (ChunkRequest) o;
        return this.x == that.x &&
                this.z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.z);
    }

    /**
     * Get the center position of this chunk square
     *
     * @return center location of this square
     */
    public BlockPosition getCenterPosition() {
        return new BlockPosition((this.x * 16) + 8, 260, (this.z * 16) + 8);
    }

}
