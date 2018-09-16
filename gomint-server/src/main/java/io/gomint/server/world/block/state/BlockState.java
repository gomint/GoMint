/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import lombok.Getter;
import lombok.Setter;

/**
 * @author geNAZt
 * @version 1.0
 * @param <T> type of the state
 */
public abstract class BlockState<T> {

    @Getter @Setter
    private T state;

    /**
     * Get the data for the block
     *
     * @return byte data for the block
     */
    public abstract short toData();

    /**
     * Get state from block data
     *
     * @param data from the block
     */
    public abstract void fromData( short data );

}
