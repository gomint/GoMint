package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.CobblestoneType;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemCobblestoneWall extends ItemStack<ItemCobblestoneWall> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemCobblestoneWall create(int amount ) {
        return GoMint.instance().createItemStack( ItemCobblestoneWall.class, amount );
    }

    /**
     * Set the type of cobble stone
     *
     * @param type of cobble stone
     */
    ItemCobblestoneWall type(CobblestoneType type);

    /**
     * Get the type of cobble stone
     *
     * @return type of cobble stone
     */
    CobblestoneType type();

}
