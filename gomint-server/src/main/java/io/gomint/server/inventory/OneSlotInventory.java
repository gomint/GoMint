/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.inventory.InventoryType;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class OneSlotInventory extends Inventory {

    public OneSlotInventory(Items items, InventoryHolder owner) {
        super(items, owner, 1);
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
