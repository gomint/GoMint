package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.inventory.item.data.DyeType;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemDye extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemDye create( int amount ) {
        return GoMint.instance().createItemStack( ItemDye.class, amount );
    }

    /**
     * Set the type of this dye
     *
     * @param type of dye
     */
    void setDyeType( DyeType type );

    /**
     * Get the type of dye
     *
     * @return type of dye
     */
    DyeType getDyeType();

}
