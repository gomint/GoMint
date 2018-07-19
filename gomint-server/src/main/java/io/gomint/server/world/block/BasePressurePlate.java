/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.entity.Entity;
import io.gomint.server.world.block.state.BooleanBlockState;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class BasePressurePlate extends Block {

    private BooleanBlockState activated = new BooleanBlockState();

    @Override
    public void stepOn( Entity entity ) {
        // Check for additional temporary data
        Integer amountOfEntitiesOn = this.storeInTemporaryStorage( "amountOfEntitiesOn", (Function<Integer, Integer>) old -> {
            if ( old == null ) return 1;
            return old + 1;
        } );

        if ( amountOfEntitiesOn > 0 && this.getBlockData() != 1 ) {
            this.activated.setState( true );
            this.updateBlock();
        }
    }

    @Override
    public void gotOff( Entity entity ) {
        Integer amountOfEntitiesOn = this.storeInTemporaryStorage( "amountOfEntitiesOn", (Function<Integer, Integer>) old -> {
            // For some weird reason a player can enter and leave a block in the same tick
            if ( old == null ) return null;

            if ( old - 1 == 0 ) return null;
            return old - 1;
        } );

        if ( amountOfEntitiesOn == null && this.getBlockData() == 1 ) {
            this.activated.setState( false );
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

    @Override
    public void generateBlockStates() {
        this.activated.fromData( (byte) ( this.getBlockData() & 0x01 ) );
    }

    @Override
    public void calculateBlockData() {
        this.resetBlockData();
        this.addToBlockData( this.activated.toData() );
    }

}
