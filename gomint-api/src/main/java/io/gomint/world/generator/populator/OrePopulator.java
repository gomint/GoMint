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
import io.gomint.world.generator.object.Ore;
import io.gomint.world.generator.object.OreType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class OrePopulator implements Populator {

    private final OreType[] oreTypes;

    public OrePopulator(OreType[] oreTypes) {
        this.oreTypes = oreTypes;
    }

    @Override
    public void populate( World world, Chunk chunk, FastRandom random ) {
        for ( OreType type : this.oreTypes ) {
            Ore ore = new Ore( random, type );
            for ( int i = 0; i < type.getClusterCount(); i++ ) {
                int x = random.nextInt( 15 ) + chunk.getX() * 16;
                int y = random.nextInt( type.getMaxHeight() - type.getMinHeight() ) + type.getMinHeight();
                int z = random.nextInt( 15 ) + chunk.getZ() * 16;

                if ( ore.canPlaceObject( world, x, y, z ) ) {
                    ore.placeObject( world, x, y, z );
                }
            }
        }
    }

}
