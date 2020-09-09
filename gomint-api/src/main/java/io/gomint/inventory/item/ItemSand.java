package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.inventory.item.data.SandType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemSand extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemSand create( int amount ) {
        return GoMint.instance().createItemStack( ItemSand.class, amount );
    }

    /**
     * Set type of sand
     *
     * @param type of sand
     */
    void setType(SandType type);

    /**
     * Get type of sand
     *
     * @return type of sand
     */
    SandType getType();

}
