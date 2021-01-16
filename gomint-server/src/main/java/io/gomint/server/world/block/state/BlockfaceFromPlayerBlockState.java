/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.util.Bearing;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockfaceFromPlayerBlockState extends BlockfaceBlockState {

    public BlockfaceFromPlayerBlockState(Supplier<String[]> key, boolean detectUpDown) {
        super(key, detectUpDown);
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving<?> player, ItemStack<?> placedItem, Facing face) {
        if (player == null) {
            this.setState(newBlock, Facing.EAST);
            return;
        }

        if (this.detectUpDown) {
            if (player.pitch() < -60) {
                this.setState(newBlock, Facing.DOWN);
                return;
            } else if (player.pitch() > 60) {
                this.setState(newBlock, Facing.UP);
                return;
            }
        }

        Bearing bearing = Bearing.fromAngle(player.yaw());
        bearing = bearing.opposite();
        this.setState(newBlock, bearing.toBlockFace());
    }

}
