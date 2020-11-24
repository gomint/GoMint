/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.populator;

import io.gomint.util.random.FastRandom;
import io.gomint.world.Biome;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockType;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class GroundPopulator implements Populator {

    @Override
    public void populate( World world, Chunk chunk, FastRandom random ) {
        for ( int x = 0; x < 16; ++x ) {
            for ( int z = 0; z < 16; ++z ) {
                Biome biome = chunk.getBiome( x, z );
                List<Block> ground = biome.getGround();
                if ( ground != null ) {
                    // Find highest point in column
                    int y = biome.getMaxElevation();
                    for ( ; y > 0; --y ) {
                        Block block = chunk.getBlockAt( x, y, z );
                        if ( !block.isTransparent() && block.getBlockType() != BlockType.AIR ) {
                            break;
                        }
                    }

                    int startY = Math.min( 127, y );
                    int endY = startY - ground.size();
                    for ( y = startY; y > endY && y >= 0; --y ) {
                        Block block = ground.get( startY - y );
                        chunk.setBlock( x, y, z, block );
                    }
                }
            }
        }
    }

}
