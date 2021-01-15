/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.testplugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.inventory.InventoryOpenEvent;
import io.gomint.inventory.InventoryType;
import io.gomint.inventory.item.ItemDiamondSword;
import io.gomint.inventory.item.ItemLapisLazuli;

public class InventoryOpenListener implements EventListener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.inventory().inventoryType() == InventoryType.ENCHANTING_TABLE) {
            ItemLapisLazuli lapisLazuli = ItemLapisLazuli.create(3);
            ItemDiamondSword diamondSword = ItemDiamondSword.create(1);

            event.inventory().item(0, diamondSword);
            event.inventory().item(1, lapisLazuli);

            event.player().level(30);
        }
    }

}
