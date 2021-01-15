package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 * @stability 1
 */
public interface ItemWarpedFungusOnAStick extends ItemStack<ItemWarpedFungusOnAStick> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemWarpedFungusOnAStick create( int amount ) {
        return GoMint.instance().createItemStack( ItemWarpedFungusOnAStick.class, amount );
    }
}
