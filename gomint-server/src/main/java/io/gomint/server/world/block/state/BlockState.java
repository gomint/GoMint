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

import java.util.function.Function;

/**
 * @param <T> type of the state
 * @author geNAZt
 * @version 1.0
 */
public abstract class BlockState<T, S> {

    private Function<S, String[]> key;

    public BlockState(Function<S, String[]> key) {
        // Store the key
        this.key = key;
    }

    public void setState(Block block, T state) {
        this.calculateValueFromState(block, state);
        block.updateBlock();
    }

    protected abstract void calculateValueFromState(Block block, T state);

    /**
     * Detect from a player
     *
     * @param newBlock
     * @param entity        from which we generate data
     * @param placedItem    which has been used to get this block
     * @param face          which the client has clicked on
     */
    public abstract void detectFromPlacement(Block newBlock, EntityLiving entity, ItemStack placedItem, Facing face);

    /**
     * Store new value for this block state
     *
     * @param value
     */
    protected void setValue(Block block, S value) {
        block.setState(this.key.apply(value), value);
    }

    protected S getValue(Block block) {
        for (String s : this.key.apply(null)) {
            S v = (S) block.getState(s);
            if (v != null) {
                return v;
            }
        }

        return null;
    }

    public abstract T getState( Block block );

}
