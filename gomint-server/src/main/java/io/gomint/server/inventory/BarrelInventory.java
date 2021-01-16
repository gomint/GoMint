/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.type.WindowType;

public class BarrelInventory extends ContainerInventory<io.gomint.inventory.BarrelInventory> implements io.gomint.inventory.BarrelInventory {

    /**
     * Create new chest inventory
     *
     * @param owner tile entity of the chest
     * @param items factory
     */
    public BarrelInventory(Items items, InventoryHolder owner) {
        super(items, owner, 27);
    }

    @Override
    public WindowType getType() {
        return WindowType.CONTAINER;
    }

    @Override
    public void onOpen(EntityPlayer player) {

    }

    @Override
    public void onClose(EntityPlayer player) {

    }

}
