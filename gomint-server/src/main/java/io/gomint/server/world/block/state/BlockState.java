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
import lombok.Getter;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @param <T> type of the state
 * @author geNAZt
 * @version 1.0
 */
public abstract class BlockState<T, S> {

    private Supplier<String> key;
    private final Block block;

    public BlockState( Block block, Supplier<String> key ) {
        // Remember to store the block
        this.block = block;

        // Store the key
        this.key = key;

        // Register this to the block
        block.registerState( this );
    }

    public void setState( T state ) {
        this.calculateValueFromState();

        if ( this.block.ready() ) {
            this.block.updateBlock();
        }
    }

    protected abstract void calculateValueFromState();

    /**
     * Detect from a player
     *
     * @param player        from which we generate data
     * @param placedItem    which has been used to get this block
     * @param face          which the client has clicked on
     * @param block         which should be replaced
     * @param clickedBlock  which has been clicked by the client
     * @param clickPosition where the client clicked on the block
     */
    public abstract void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition );

    /**
     * Store new value for this block state
     *
     * @param value
     */
    protected void setValue(S value) {
        this.block.setState(this.key.get(), value);
    }

    protected S getValue() {
        return (S) this.block.getState(this.key.get());
    }

    public abstract T getState();

}
