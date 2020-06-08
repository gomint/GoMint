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

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnumBlockState<E extends Enum<E>, T> extends BlockState<E, T> {

    private final E[] enumValues;
    private final Function<E, T> valueResolver;

    public EnumBlockState(Block block, Supplier<String> key, E[] values, Function<E, T> valueResolver) {
        super( block, key );
        this.enumValues = values;
        this.valueResolver = valueResolver;
        this.setState(values[0]);
    }

    @Override
    protected void calculateValueFromState() {
        this.setValue(this.valueResolver.apply(this.getState()));
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition ) {
        this.setState( this.enumValues[placedItem.getData()] );
    }

}
