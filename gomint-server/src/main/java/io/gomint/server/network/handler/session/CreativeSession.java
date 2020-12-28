/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler.session;

import io.gomint.server.crafting.session.SessionInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;

public class CreativeSession implements Session {

    private final Inventory outputInventory;

    public CreativeSession(PlayerConnection connection) {
        this.outputInventory = new SessionInventory(connection.getServer().getItems(),
            connection.getEntity(), 1);
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
    public void addInput(ItemStack item, int slot) {
        this.outputInventory.addItem(item);
    }

    @Override
    public void postProcess() {

    }

}
