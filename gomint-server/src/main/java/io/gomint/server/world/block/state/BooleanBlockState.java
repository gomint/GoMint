/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BooleanBlockState extends BlockState<Boolean, Byte> {

    public BooleanBlockState(Supplier<String[]> key) {
        super(v -> key.get());
    }

    @Override
    protected void calculateValueFromState(Block block, Boolean state) {
        this.setValue(block, (byte) (state ? 1 : 0));
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving player, ItemStack placedItem, Facing face) {
        this.setState(newBlock, false);
    }

    @Override
    public Boolean getState(Block block) {
        Byte val = this.getValue(block);
        return val != null && val == 1;
    }

}
