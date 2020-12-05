/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting.session;

import io.gomint.inventory.InventoryType;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;

/**
 * @author geNAZt
 *
 * This inventory is used to hold items which are "consumed" by the crafting action issued
 */
public class SessionInventory extends Inventory {

    public SessionInventory(Items items, InventoryHolder owner, int size) {
        super(items, owner, size);
    }

    @Override
    public void sendContents(PlayerConnection playerConnection) {

    }

    @Override
    public void sendContents(int slot, PlayerConnection playerConnection) {

    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CRAFTING;
    }

}
