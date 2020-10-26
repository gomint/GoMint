package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.PumpkinType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemPumpkin extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemPumpkin create( int amount ) {
        return GoMint.instance().createItemStack( ItemPumpkin.class, amount );
    }

    /**
     * Get the type of pumpkin
     *
     * @return type of pumpkin
     */
    PumpkinType getType();

    /**
     * Set the type of pumpkin
     *
     * @param type of pumpkin
     */
    void setType(PumpkinType type);

}
