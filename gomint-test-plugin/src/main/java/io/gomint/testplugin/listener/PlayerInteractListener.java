/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.testplugin.listener;

import io.gomint.GoMint;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.projectile.EntityArrow;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerInteractEvent;
import io.gomint.inventory.item.ItemArrow;
import io.gomint.inventory.item.ItemStack;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerInteractListener implements EventListener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        EntityPlayer player = e.player();
        ItemStack<?> itemStack = player.inventory().itemInHand();
        player.sendMessage(Objects.toString(itemStack));

        if (itemStack instanceof ItemArrow) {
            EntityArrow entityArrow = GoMint.instance().createEntity(EntityArrow.class);
            entityArrow.spawn(player.location().add(0, (float) 1.5, 0));
            entityArrow.velocity(player.direction().multiply(2));
        }
    }

}
