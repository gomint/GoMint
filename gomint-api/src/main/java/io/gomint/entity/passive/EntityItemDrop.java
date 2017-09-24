package io.gomint.entity.passive;

import io.gomint.inventory.item.ItemStack;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityItemDrop {

    /**
     * Get the ItemStack which has been stored in this entity. You can modify it but it won't update
     * the entity.
     *
     * @param <T> generic type of the item stack
     * @return the ItemStack which has been stored
     */
    <T extends ItemStack> T getItemStack();

    /**
     * Set a new pickup delay
     *
     * @param duration the amount of timeUnit to wait
     * @param timeUnit the unit of time to wait
     */
    void setPickupDelay( long duration, TimeUnit timeUnit );

    /**
     * Get the time when the item drop is allowed to be picked up
     *
     * @return the unix timestamp in millis when the item drop can be picked up
     */
    long getPickupTime();

}
