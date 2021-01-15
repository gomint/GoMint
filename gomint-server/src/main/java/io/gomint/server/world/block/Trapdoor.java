/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockTrapdoor;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

import java.util.Collections;
import java.util.List;

public abstract class Trapdoor<B> extends Block implements BlockTrapdoor<B> {

    private static final DirectionBlockState DIRECTION = new DirectionBlockState( () -> new String[]{"direction"});
    private static final BooleanBlockState TOP = new BooleanBlockState( () -> new String[]{"upside_down_bit"});
    private static final BooleanBlockState OPEN = new BooleanBlockState( () -> new String[]{"open_bit"});

    @Override
    public boolean open() {
        return OPEN.getState(this);
    }

    @Override
    public B toggle() {
        OPEN.setState(this, !this.open());
        return (B) this;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack<?> item) {
        toggle();
        return true;
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack<?> item, Facing face, Location location) {
        DIRECTION.detectFromPlacement(this, entity, item, face);
        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        float defaultHeight = 0.1875f;

        // Basis box
        AxisAlignedBB bb;
        if (TOP.getState(this)) {
            // Top closed box
            bb = new AxisAlignedBB(
                this.location.getX(),
                this.location.getY() + 1 - defaultHeight,
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 1,
                this.location.getZ() + 1
            );
        } else {
            // Bottom closed box
            bb = new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + defaultHeight,
                this.location.getZ() + 1
            );
        }

        // Check for open state
        if (OPEN.getState(this)) {
            switch (DIRECTION.getState(this)) {
                case NORTH:
                    bb.bounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ() + 1 - defaultHeight,
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;

                case SOUTH:
                    bb.bounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + defaultHeight
                    );

                    break;

                case WEST:
                    bb.bounds(
                        this.location.getX() + 1 - defaultHeight,
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;

                case EAST:
                    bb.bounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + defaultHeight,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;
            }
        }

        return Collections.singletonList(bb);
    }

    @Override
    public B direction(Direction direction) {
        DIRECTION.setState(this, direction);
        return (B) this;
    }

    @Override
    public Direction direction() {
        return DIRECTION.getState(this);
    }

}
