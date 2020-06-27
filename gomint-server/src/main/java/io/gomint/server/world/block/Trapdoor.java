/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockTrapdoor;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

import java.util.Collections;
import java.util.List;

public abstract class Trapdoor extends Block implements BlockTrapdoor {

    private final DirectionBlockState direction = new DirectionBlockState(this, () -> new String[]{"direction"});
    private final BooleanBlockState top = new BooleanBlockState(this, () -> new String[]{"upside_down_bit"});
    private final BooleanBlockState open = new BooleanBlockState(this, () -> new String[]{"open_bit"});

    @Override
    public boolean isOpen() {
        return this.open.getState();
    }

    @Override
    public void toggle() {
        this.open.setState(!this.isOpen());
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        toggle();
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        float defaultHeight = 0.1875f;

        // Basis box
        AxisAlignedBB bb;
        if (this.top.getState()) {
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
        if (this.open.getState()) {
            switch (this.direction.getState()) {
                case NORTH:
                    bb.setBounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ() + 1 - defaultHeight,
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;

                case SOUTH:
                    bb.setBounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + defaultHeight
                    );

                    break;

                case WEST:
                    bb.setBounds(
                        this.location.getX() + 1 - defaultHeight,
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;

                case EAST:
                    bb.setBounds(
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
    public void setDirection(Direction direction) {
        this.direction.setState(direction);
    }

    @Override
    public Direction getDirection() {
        return this.direction.getState();
    }

}
