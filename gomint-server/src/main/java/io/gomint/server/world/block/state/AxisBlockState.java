/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
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
                this.value(block, "x");
                break;
            case Y:
                this.value(block, "y");
                break;
            case Z:
                this.value(block, "z");
                break;
        }
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving<?> player, ItemStack<?> placedItem, Facing face, Vector clickVector) {
        if (face == null) {
            this.state(newBlock, Axis.Z);
            return;
        }

        switch (face) {
            case UP:
            case DOWN:
                this.state(newBlock, Axis.Y);
                break;

            case NORTH:
            case SOUTH:
                this.state(newBlock, Axis.Z);
                break;

            default:
                this.state(newBlock, Axis.X);
                break;
        }
    }

    @Override
    public Axis state(Block block) {
        switch (this.value(block)) {
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
