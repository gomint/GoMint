/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler.session;

import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.OneSlotInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;

public class CreativeSession implements Session {

    private final Inventory outputInventory;

    public CreativeSession(PlayerConnection connection) {
        this.outputInventory = new OneSlotInventory(connection.getServer().getItems(),
            connection.getEntity());
    }

    @Override
    public Inventory getOutput() {
        return this.outputInventory;
    }

    @Override
    public boolean process() {
        return true;
    }

    @Override
    public void addInput(ItemStack item) {
        this.outputInventory.addItem(item);
    }

}
