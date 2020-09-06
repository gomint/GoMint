/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemSlab extends ItemStack implements io.gomint.inventory.item.ItemSlab {

    @Override
    public boolean isTop() {
        return this.getDamage() >= 8;
    }

    @Override
    public void setTop(boolean top) {
        this.setData((short) (this.getData() ^ 8));
    }

}
