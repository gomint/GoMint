/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Axis;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

public class AxisBlockState extends BlockState<Axis, String> {

    public AxisBlockState(Block block, Supplier<String[]> key) {
        super(block, v -> key.get());
        this.setState(Axis.Y);
    }

    @Override
    protected void calculateValueFromState(Axis state) {
        switch (state) {
            case X:
                this.setValue("x");
                break;
            case Y:
                this.setValue("y");
                break;
            case Z:
                this.setValue("z");
                break;
        }
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        switch ( face ) {
            case UP:
            case DOWN:
                this.setState( Axis.Y );
                break;

            case NORTH:
            case SOUTH:
                this.setState( Axis.X );
                break;

            default:
                this.setState( Axis.Z );
                break;
        }
    }

    @Override
    public Axis getState() {
        switch (this.getValue()) {
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
