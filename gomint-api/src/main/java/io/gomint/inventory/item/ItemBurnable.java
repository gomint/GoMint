package io.gomint.inventory.item;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemBurnable {

    /**
     * Get the time in milliseconds this item can burn for
     *
     * @return amount of milliseconds this item can burn for
     */
    long getBurnTime();

}
