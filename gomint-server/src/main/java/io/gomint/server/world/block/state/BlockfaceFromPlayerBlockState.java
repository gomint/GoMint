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
    public void detectFromPlacement(Block newBlock, EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        if (face == null) {
            this.setState(newBlock, Facing.NORTH);
            return;
        }

        if (!this.detectUpDown && (face == Facing.UP || face == Facing.DOWN)) {
            face = Facing.NORTH;
        }

        this.setState(newBlock, face);
    }

}
