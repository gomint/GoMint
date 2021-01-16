/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.object;

import io.gomint.util.random.FastRandom;
import io.gomint.world.World;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockDirt;
import io.gomint.world.block.BlockLeaves;
import io.gomint.world.block.BlockLog;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Tree<E> {

    private static final BlockType[] OVERRIDABLE = new BlockType[]{
        BlockType.AIR, BlockType.SAPLING, BlockType.LOG, BlockType.LEAVES, BlockType.SNOW_LAYER
    };

    protected int treeHeight;
    protected BlockLog trunkBlock;
    protected BlockLeaves leafBlock;

    public abstract E grow(World world, int x, int y, int z, FastRandom random);

    /**
     * Check if we can place given object
     *
     * @param world  which we want this object in
     * @param x      position of the object start
     * @param y      position of the object start
     * @param z      position of the object start
     * @param random with which we want to generate
     * @return true when it can be placed, false otherwise
     */
    public boolean canPlaceObject( World world, int x, int y, int z, FastRandom random ) {
        int radiusToCheck = 0;
        for ( int yy = 0; yy < this.treeHeight + 3; ++yy ) {
            if ( yy == 1 || yy == this.treeHeight ) {
                ++radiusToCheck;
            }

            for ( int xx = -radiusToCheck; xx < ( radiusToCheck + 1 ); ++xx ) {
                for ( int zz = -radiusToCheck; zz < ( radiusToCheck + 1 ); ++zz ) {
                    Block block = world.blockAt( x + xx, y + yy, z + zz );

                    boolean foundOverride = false;
                    for ( BlockType blockType : OVERRIDABLE ) {
                        if ( block.blockType() == blockType ) {
                            foundOverride = true;
                            break;
                        }
                    }

                    if ( !foundOverride ) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public E placeObject( World world, int x, int y, int z, FastRandom random ) {
        this.placeTrunk( world, x, y, z, random, this.treeHeight - 1 );
        for ( int yy = y - 3 + this.treeHeight; yy <= y + this.treeHeight; ++yy ) {
            int yOff = yy - ( y + this.treeHeight );
            int mid = (int) ( 1 - yOff / (double) 2 );

            for ( int xx = x - mid; xx <= x + mid; ++xx ) {
                int xOff = Math.abs( xx - x );
                for ( int zz = z - mid; zz <= z + mid; ++zz ) {
                    int zOff = Math.abs( zz - z );
                    if ( xOff == mid && zOff == mid && ( yOff == 0 || random.nextInt( 2 ) == 0 ) ) {
                        continue;
                    }

                    Block replace = world.blockAt( xx, yy, zz );
                    if ( !replace.solid() ) {
                        replace.copyFromBlock( this.leafBlock );
                    }
                }
            }
        }

        return (E) this;
    }

    protected void placeTrunk( World world, int x, int y, int z, FastRandom random, int trunkHeight ) {
        // The base dirt block
        world.blockAt( x, y - 1, z ).blockType( BlockDirt.class );
        for ( int yy = 0; yy < trunkHeight; ++yy ) {
            Block block = world.blockAt( x, y + yy, z );

            for ( BlockType type : OVERRIDABLE ) {
                if ( block.blockType() == type ) {
                    world.blockAt( x, y + yy, z ).copyFromBlock( this.trunkBlock );
                }
            }
        }
    }

}
