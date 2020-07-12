/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
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
public class DirectionBlockState extends BlockState<Direction, Integer> {

    public DirectionBlockState(Block block, Supplier<String[]> key) {
        this(block, key, 0);
    }

    @Override
    protected void calculateValueFromState(Direction state) {
        switch (state) {
            case NORTH:
                this.setValue(2);
                break;
            case EAST:
                this.setValue(3);
                break;
            case WEST:
                this.setValue(1);
                break;
            case SOUTH:
            default:
                this.setValue(0);
                break;
        }
    }

    public DirectionBlockState(Block block, Supplier<String[]> key, int rotation) {
        super(block, v -> key.get());
        this.setValue(rotation);
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        if (player == null) {
            this.setState(Direction.SOUTH);
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
                return Direction.SOUTH;
            case 1:
                return Direction.WEST;
            case 2:
                return Direction.NORTH;
            case 3:
                return Direction.EAST;
        }
    }

}
