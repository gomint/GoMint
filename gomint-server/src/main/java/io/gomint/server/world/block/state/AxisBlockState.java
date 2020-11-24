/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Axis;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

public class AxisBlockState extends BlockState<Axis, String> {

    public AxisBlockState(Supplier<String[]> key) {
        super(v -> key.get());
    }

    @Override
    protected void calculateValueFromState(Block block, Axis state) {
        switch (state) {
            case X:
                this.setValue(block, "x");
                break;
            case Y:
                this.setValue(block, "y");
                break;
            case Z:
                this.setValue(block, "z");
                break;
        }
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving player, ItemStack placedItem, Facing face) {
        if (face == null) {
            this.setState(newBlock, Axis.Z);
            return;
        }

        switch (face) {
            case UP:
            case DOWN:
                this.setState(newBlock, Axis.Y);
                break;

            case NORTH:
            case SOUTH:
                this.setState(newBlock, Axis.X);
                break;

            default:
                this.setState(newBlock, Axis.Z);
                break;
        }
    }

    @Override
    public Axis getState(Block block) {
        switch (this.getValue(block)) {
            case "x":
                return Axis.X;
            case "z":
                return Axis.Z;
            case "y":
            default:
                return Axis.Y;
        }
    }

}
