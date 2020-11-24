/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.object;

import io.gomint.math.Vector2;
import io.gomint.util.random.FastRandom;
import io.gomint.world.World;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Ore {

    private FastRandom random;
    private OreType type;

    public Ore( FastRandom random, OreType type ) {
        this.type = type;
        this.random = random;
    }

    public boolean canPlaceObject( World world, int x, int y, int z ) {
        return world.getBlockAt( x, y, z ).getBlockType() == BlockType.STONE;
    }

    public void placeObject( World world, int x, int y, int z ) {
        int clusterSize = this.type.getClusterSize();
        double angle = this.random.nextFloat() * Math.PI;

        Vector2 offset = Vector2.getDirection2D( angle ).multiply( clusterSize / 8 );
        float x1 = x + 8 + offset.getX();
        float x2 = x + 8 - offset.getX();
        float z1 = z + 8 + offset.getZ();
        float z2 = z + 8 - offset.getZ();

        int y1 = y + this.random.nextInt( 3 ) + 2;
        int y2 = y + this.random.nextInt( 3 ) + 2;

        for ( int count = 0; count <= clusterSize; ++count ) {
            double seedX = ( x1 + ( x2 - x1 ) * count / clusterSize );
            double seedY = y1 + ( y2 - y1 ) * count / clusterSize;
            double seedZ = ( z1 + ( z2 - z1 ) * count / clusterSize );

            double size = ( ( ( Math.sin( count * ( Math.PI / clusterSize ) ) + 1 ) * this.random.nextFloat() * clusterSize / 16 + 1 ) / 2 );
            int startX = (int) ( seedX - size );
            int startY = (int) ( seedY - size );
            int startZ = (int) ( seedZ - size );
            int endX = (int) ( seedX + size );
            int endY = (int) ( seedY + size );
            int endZ = (int) ( seedZ + size );

            for ( x = startX; x <= endX; ++x ) {
                double sizeX = ( x + 0.5 - seedX ) / size;
                sizeX *= sizeX;
                if ( sizeX < 1 ) {
                    for ( y = startY; y <= endY; ++y ) {
                        double sizeY = ( y + 0.5 - seedY ) / size;
                        sizeY *= sizeY;
                        if ( y > 0 && ( sizeX + sizeY ) < 1 ) {
                            for ( z = startZ; z <= endZ; ++z ) {
                                double sizeZ = ( z + 0.5 - seedZ ) / size;
                                sizeZ *= sizeZ;
                                if ( ( sizeX + sizeY + sizeZ ) < 1 && world.getBlockAt( x, y, z ).getBlockType() == BlockType.STONE ) {
                                    world.getBlockAt( x, y, z ).copyFromBlock( this.type.getBlock() );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
