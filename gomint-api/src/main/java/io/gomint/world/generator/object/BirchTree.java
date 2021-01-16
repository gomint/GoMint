/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.object;

import io.gomint.GoMint;
import io.gomint.util.random.FastRandom;
import io.gomint.world.World;
import io.gomint.world.block.BlockLeaves;
import io.gomint.world.block.BlockLog;
import io.gomint.world.block.data.Axis;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BirchTree extends Tree<BirchTree> {

    public BirchTree() {
        this.leafBlock = GoMint.instance().createBlock( BlockLeaves.class );
        this.leafBlock.type( LogType.BIRCH );

        this.trunkBlock = GoMint.instance().createBlock( BlockLog.class );
        this.trunkBlock.type( LogType.BIRCH );
        this.trunkBlock.stripped(false);
        this.trunkBlock.barkOnAllSides(false);
        this.trunkBlock.axis(Axis.Y);
    }

    @Override
    public BirchTree grow( World world, int x, int y, int z, FastRandom random ) {
        this.treeHeight = random.nextInt( 3 ) + 4;
        if ( this.canPlaceObject( world, x, y, z, random ) ) {
            this.placeObject( world, x, y, z, random );
        }

        return this;
    }

}
