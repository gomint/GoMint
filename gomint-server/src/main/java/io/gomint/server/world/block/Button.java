/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.world.block.BlockButton;
import io.gomint.world.block.data.Facing;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Button<B> extends Block implements BlockButton<B> {

    private static final BlockfaceFromPlayerBlockState FACING = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, true);
    private static final BooleanBlockState PRESSED = new BooleanBlockState(() -> new String[]{"button_pressed_bit"});

    @Override
    public boolean interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        // Press the button
        this.press();

        return true;
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location, Vector clickVector) {
        FACING.detectFromPlacement(this, entity, item, face, clickVector);
        return true;
    }

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        if (updateReason == UpdateReason.SCHEDULED && pressed()) {
            PRESSED.state(this, false);
        }

        return -1;
    }

    @Override
    public boolean pressed() {
        return PRESSED.state(this);
    }

    @Override
    public B press() {
        // Check if we need to update
        if (!pressed()) {
            PRESSED.state(this, true);
        }

        // Schedule release in 1 second
        this.world.scheduleBlockUpdate(this.location, 1, TimeUnit.SECONDS);
        return (B) this;
    }

    @Override
    public Facing facing() {
        return FACING.state(this);
    }

    @Override
    public B facing(Facing face) {
        FACING.state(this, face);
        return (B) this;
    }

}
