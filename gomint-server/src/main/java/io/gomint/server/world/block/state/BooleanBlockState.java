/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
public class BooleanBlockState extends BlockState<Boolean, Byte> {

    public BooleanBlockState(Block block, Supplier<String> key) {
        super(block, key);
        this.setState(false);
    }

    @Override
    protected void calculateValueFromState() {
        this.setValue((byte) (this.getState() ? 1 : 0));
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        this.setState(false);
    }

    @Override
    public Boolean getState() {
        return this.getValue() == 1;
    }

}
