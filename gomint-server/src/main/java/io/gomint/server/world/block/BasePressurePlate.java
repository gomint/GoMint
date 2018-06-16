/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.entity.Entity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class BasePressurePlate extends Block {

    @Override
    public void stepOn( Entity entity ) {
        // Check for additional temporary data
        Integer amountOfEntitiesOn = this.storeInTemporaryStorage( "amountOfEntitiesOn", new Function<Integer, Integer>() {
            @Override
            public Integer apply( Integer old ) {
                if ( old == null ) return 1;
                return old + 1;
            }
        } );

        if ( amountOfEntitiesOn > 0 && this.getBlockData() != 1 ) {
            this.setBlockData( (byte) 1 );
            this.updateBlock();
        }
    }

    @Override
    public void gotOff( Entity entity ) {
        Integer amountOfEntitiesOn = this.storeInTemporaryStorage( "amountOfEntitiesOn", new Function<Integer, Integer>() {
            @Override
            public Integer apply( Integer old ) {
                // For some weird reason a player can enter and leave a block in the same tick
                if ( old == null ) return null;

                if ( old - 1 == 0 ) return null;
                return old - 1;
            }
        } );

        if ( amountOfEntitiesOn == null && this.getBlockData() == 1 ) {
            this.setBlockData( (byte) 0 );
            this.updateBlock();
        }
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX() + 0.0625f,
            this.location.getY(),
            this.location.getZ() + 0.0625f,
            this.location.getX() + 0.9375f,
            this.location.getY() + 0.0625f,
            this.location.getZ() + 0.9375f
        ) );
    }

}
