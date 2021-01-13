/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.integrated;

import io.gomint.math.BlockPosition;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;

/**
 * @author geNAZt
 * @version 1.0
 */
public class VanillaGenerator extends ChunkGenerator {

    public static final String NAME = "vanilla";

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public VanillaGenerator( World world, GeneratorContext context ) {
        super( world, context );
    }

    @Override
    public Chunk generate( int x, int z ) {
        return null;
    }

    @Override
    public BlockPosition spawnPoint() {
        return null;
    }

    @Override
    public void populate( Chunk chunk ) {

    }

}
