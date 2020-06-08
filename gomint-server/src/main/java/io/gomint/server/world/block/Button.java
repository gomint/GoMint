/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.world.block.BlockButton;
import io.gomint.world.block.data.Facing;

import java.util.concurrent.TimeUnit;

public abstract class Button extends Block implements BlockButton {

    private final BlockfaceFromPlayerBlockState facing = new BlockfaceFromPlayerBlockState( this, () -> "facing_direction", true );
    private final BooleanBlockState pressed = new BooleanBlockState( this, () -> "button_pressed_bit" );

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        // Press the button
        this.press();

        return true;
    }

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT ) {
        if ( updateReason == UpdateReason.SCHEDULED && isPressed() ) {
            this.pressed.setState( false );
        }

        return -1;
    }

    @Override
    public boolean isPressed() {
        return this.pressed.getState();
    }

    @Override
    public void press() {
        // Check if we need to update
        if ( !isPressed() ) {
            this.pressed.setState( true );
        }

        // Schedule release in 1 second
        this.world.scheduleBlockUpdate( this.location, 1, TimeUnit.SECONDS );
    }

    @Override
    public Facing getAttachedFace() {
        return this.facing.getState();
    }

    @Override
    public void setAttachedFace( Facing face ) {
        this.facing.setState( face );
    }

    @Override
    public void setFacing(Facing facing) {
        this.facing.setState(facing);
    }

    @Override
    public Facing getFacing() {
        return this.facing.getState();
    }

}
