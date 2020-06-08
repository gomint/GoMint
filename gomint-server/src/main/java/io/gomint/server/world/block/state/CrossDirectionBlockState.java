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

    public CrossDirectionBlockState(Block block, Supplier<String> key) {
        super(block, key);
    }

    @Override
    protected void calculateValueFromState() {
        switch (this.getState()) {
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
        Bearing bearing = Bearing.fromAngle(player.getYaw());
        this.setState(bearing.toDirection());
    }

}
