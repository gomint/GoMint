/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.entity.passive.EntityItemDrop;
import io.gomint.inventory.item.ItemStack;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = true )
@ToString( callSuper = true )
public class PlayerPickupItemEvent extends CancellablePlayerEvent {

    private final Entity holdingEntity;
    private final ItemStack itemStack;

    public PlayerPickupItemEvent( EntityPlayer player, Entity holdingEntity, ItemStack itemStack ) {
        super( player );
        this.itemStack = itemStack;
        this.holdingEntity = holdingEntity;
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
    public Entity getHoldingEntity() {
        return this.holdingEntity;
    }

}
