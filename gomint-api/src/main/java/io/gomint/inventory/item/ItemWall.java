package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemWall extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemWall create(int amount ) {
        return GoMint.instance().createItemStack( ItemWall.class, amount );
    }

    /**
     * Get the type of stone from which this wall has been made
     *
     * @return type of stone
     */
    StoneType getStoneType();

    /**
     * Set the type of stone for this wall
     *
     * @param stoneType type of stone
     */
    void setStoneType(StoneType stoneType);

}
