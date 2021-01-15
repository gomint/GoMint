/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemReduceBreaktime;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemReduceTierSpecial<I extends io.gomint.inventory.item.ItemStack<I>> extends ItemStack<I> implements ItemReduceBreaktime<I> {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public float divisor() {
        return 15;
    }

}
