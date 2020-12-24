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

public class EnchantingSession implements Session {

    private final Inventory outputInventory;
    private final PlayerConnection connection;

    public EnchantingSession(PlayerConnection connection) {
        this.connection = connection;
        this.outputInventory = new OneSlotInventory(connection.getServer().getItems(),
            connection.getEntity());
    }

    @Override
    public Inventory getOutput() {
        return this.outputInventory;
    }

    @Override
    public boolean process() {
        return false;
    }

    @Override
    public void addInput(ItemStack item) {

    }

}
