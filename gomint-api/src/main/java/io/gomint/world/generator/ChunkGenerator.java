/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator;

import io.gomint.math.BlockPosition;
import io.gomint.world.Chunk;
import io.gomint.world.World;

/**
 * @author Clockw1seLrd
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public abstract class ChunkGenerator {

    protected World world;
    protected GeneratorContext context;

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public ChunkGenerator( World world, GeneratorContext context ) {
        this.world = world;
        this.context = context;
    }

    public GeneratorContext context() {
        return this.context;
    }

    /**
     * Generate a chunk at the given coordinates. You have to return a fully built chunk, you can request one with
     * {@link World#generateEmptyChunk(int, int)}
     *
     * @param x coordinate of the chunk
     * @param z coordinate of the chunk
     * @return proper populated chunk
     */
    public abstract Chunk generate( int x, int z );

    /**
     * When you generate a new world using this chunk generator this method returns the worlds spawn. At this stage
     * no blocks are loaded.
     *
     * @return block position of the spawn of this new world
     */
    public abstract BlockPosition spawnPoint();

    /**
     * Populate (generate additional objects) for the given chunk
     *
     * @param chunk which should be populated
     */
    public abstract void populate( Chunk chunk );

    /**
     * Close the generator
     */
    public void close() {

    }

}
