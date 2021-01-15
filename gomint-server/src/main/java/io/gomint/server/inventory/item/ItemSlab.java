/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemSlab<I extends io.gomint.inventory.item.ItemStack<I>> extends ItemStack<I> implements io.gomint.inventory.item.ItemSlab<I> {

    @Override
    public boolean top() {
        return this.damage() >= 8;
    }

    @Override
    public I top(boolean top) {
        this.data((short) (this.data() ^ 8));
        return (I) this;
    }

}
