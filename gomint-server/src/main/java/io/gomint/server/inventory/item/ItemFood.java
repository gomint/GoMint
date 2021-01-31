/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.inventory.item;

import io.gomint.event.player.PlayerConsumeItemEvent;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.category.ItemConsumable;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemFood<I extends io.gomint.inventory.item.ItemStack<I>> extends ItemStack<I> implements io.gomint.inventory.item.ItemFood<I>, ItemConsumable {

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock) {
        // TODO: Check fo planting

        if (entity.isHungry() && clickedBlock == null) {
            if (entity.actionStart() > -1) {
                // Call event
                PlayerConsumeItemEvent consumeItemEvent = new PlayerConsumeItemEvent(entity, this);
                entity.world().server().pluginManager().callEvent(consumeItemEvent);

                if (consumeItemEvent.cancelled()) {
                    return false;
                }

                // Consume
                this.onConsume(entity);
                entity.resetActionStart();
            } else {
                entity.setUsingItem(true);
            }
        }

        return super.interact(entity, face, clickPosition, clickedBlock);
    }

    @Override
    public void onConsume(EntityPlayer player) {
        if (player.isHungry()) {
            player.addHunger(getHunger());

            float saturation = Math.min(player.saturation() + (getHunger() * getSaturation() * 2.0f), player.hunger());
            player.saturation(saturation);

            // Default manipulation
            this.afterPlacement();
        }
    }

}
