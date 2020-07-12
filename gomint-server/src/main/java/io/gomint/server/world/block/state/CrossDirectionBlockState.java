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
import io.gomint.server.util.Bearing;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CrossDirectionBlockState extends BlockState<Direction, Integer> {

    public CrossDirectionBlockState(Block block, Supplier<String[]> key) {
        super(block, v -> key.get());
    }

    @Override
    protected void calculateValueFromState(Direction state) {
        switch (state) {
            case NORTH:
                this.setValue(3);
                break;
            case SOUTH:
                this.setValue(2);
                break;
            case WEST:
                this.setValue(1);
                break;
            default:
                this.setValue(0);
                break;
        }
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        if (player == null) {
            this.setState(Direction.EAST);
            return;
        }

        Bearing bearing = Bearing.fromAngle(player.getYaw());
        this.setState(bearing.toDirection());
    }

    @Override
    public Direction getState() {
        switch (this.getValue()) {
            case 0:
            default:
                return Direction.EAST;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.NORTH;
        }
    }

}
