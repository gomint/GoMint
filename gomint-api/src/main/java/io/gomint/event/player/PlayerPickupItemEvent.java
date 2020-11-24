/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.inventory.item.ItemStack;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerPickupItemEvent that = (PlayerPickupItemEvent) o;
        return Objects.equals(holdingEntity, that.holdingEntity) &&
            Objects.equals(itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), holdingEntity, itemStack);
    }

    @Override
    public String toString() {
        return "PlayerPickupItemEvent{" +
            "holdingEntity=" + holdingEntity +
            ", itemStack=" + itemStack +
            '}';
    }

}
