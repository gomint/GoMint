package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface ItemWoodenDoor extends ItemStack<ItemWoodenDoor>, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemWoodenDoor create(int amount ) {
        return GoMint.instance().createItemStack( ItemWoodenDoor.class, amount );
    }

    /**
     * Get the type of wood from which this button has been made
     *
     * @return type of wood
     */
    LogType type();

    /**
     * Set the type of wood for this button
     *
     * @param logType type of wood
     */
    ItemWoodenDoor type(LogType logType);

}
