/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
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

    public DirectionBlockState(Supplier<String[]> key) {
        super(v -> key.get());
    }

    @Override
    protected void calculateValueFromState(Block block, Direction state) {
        switch (state) {
            case NORTH:
                this.value(block, 2);
                break;
            case EAST:
                this.value(block, 3);
                break;
            case WEST:
                this.value(block, 1);
                break;
            case SOUTH:
            default:
                this.value(block, 0);
                break;
        }
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving<?> player, ItemStack<?> placedItem, Facing face) {
        if (player == null) {
            this.state(newBlock, Direction.SOUTH);
            return;
        }

        Bearing bearing = Bearing.fromAngle(player.yaw());
        this.state(newBlock, bearing.toDirection());
    }

    @Override
    public Direction state(Block block) {
        switch (this.value(block)) {
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
