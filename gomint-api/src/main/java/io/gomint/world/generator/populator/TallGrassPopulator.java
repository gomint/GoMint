/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.populator;

import io.gomint.GoMint;
import io.gomint.util.random.FastRandom;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockTallGrass;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TallGrassPopulator implements Populator {

    private int randomAmount;
    private int baseAmount;

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

            if ( y != -1 && this.canTallGrassStay( chunk, x, y, z ) ) {
                BlockTallGrass tallGrass = GoMint.instance().createBlock( BlockTallGrass.class );
                tallGrass.setGrassType( BlockTallGrass.Type.GRASS );
                chunk.setBlock( x, y, z, tallGrass );
            }
        }
    }

    private boolean canTallGrassStay( Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlockAt( x, y, z );
        return ( block.getBlockType() == BlockType.AIR || block.getBlockType() == BlockType.SNOW_LAYER ) && chunk.getBlockAt( x, y - 1, z ).getBlockType() == BlockType.GRASS_BLOCK;
    }

    private int getHighestWorkableBlock( Chunk chunk, int x, int z ) {
        int y = 255;
        for ( ; y >= 0; --y ) {
            Block block = chunk.getBlockAt( x, y, z );
            if ( block.getBlockType() != BlockType.AIR && block.getBlockType() != BlockType.LEAVES && block.getBlockType() != BlockType.SNOW_LAYER ) {
                break;
            }
        }

        return y == 0 ? -1 : ++y;
    }

}
