/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityItemDrop;
import io.gomint.inventory.item.ItemStack;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerPickupItemEvent extends CancellablePlayerEvent {

    private final EntityItemDrop itemDrop;
    private final ItemStack itemStack;

    public PlayerPickupItemEvent( EntityPlayer player, EntityItemDrop itemDrop, ItemStack itemStack ) {
        super( player );
        this.itemStack = itemStack;
        this.itemDrop = itemDrop;
    }

    /**
     * Get the item stack which should be picked up
     *
     * @return item stack which should be picked up
     */
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    /**
     * Get the entity which will be destroyed when the item will be picked up
     *
     * @return the entity which currently holds the item
     */
    public EntityItemDrop getItemDrop() {
        return this.itemDrop;
    }

}
