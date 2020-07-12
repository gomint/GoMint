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
    private final Function<T, E> stateResolver;

    public EnumBlockState(Block block, Function<T, String[]> key, E[] values, Function<E, T> valueResolver, Function<T, E> stateResolver) {
        super(block, key);
        this.enumValues = values;
        this.valueResolver = valueResolver;
        this.stateResolver = stateResolver;
    }

    @Override
    protected void calculateValueFromState(E state) {
        this.setValue(this.valueResolver.apply(state));
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        this.setState(this.enumValues[placedItem == null ? 0 : placedItem.getData()]);
    }

    @Override
    public E getState() {
        return this.stateResolver.apply(this.getValue());
    }

}
