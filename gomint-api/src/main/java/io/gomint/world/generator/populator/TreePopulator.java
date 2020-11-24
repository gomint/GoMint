/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.populator;

import io.gomint.util.random.FastRandom;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockType;
import io.gomint.world.generator.object.Tree;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TreePopulator implements Populator {

    private final Tree tree;
    private int randomAmount;
    private int baseAmount;

    public TreePopulator( Tree tree ) {
        this.tree = tree;
    }

    public void setRandomAmount( int amount ) {
        this.randomAmount = amount;
    }

    public void setBaseAmount( int amount ) {
        this.baseAmount = amount;
    }

    @Override
    public void populate( World world, Chunk chunk, FastRandom random ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 15 );
            int z = random.nextInt( 15 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( y != -1 ) {
                this.tree.grow( world, chunk.getX() * 16 + x, y, chunk.getZ() * 16 + z, random );
            }
        }
    }

    private int getHighestWorkableBlock( Chunk chunk, int x, int z ) {
        int y = 255;
        for ( ; y > 0; --y ) {
            Block block = chunk.getBlockAt( x, y, z );
            if ( block.getBlockType() == BlockType.DIRT || block.getBlockType() == BlockType.GRASS_BLOCK ) {
                break;
            } else if ( block.getBlockType() != BlockType.AIR && block.getBlockType() != BlockType.SNOW_LAYER ) {
                return -1;
            }
        }

        return y == 0 ? -1 : ++y;
    }

}
