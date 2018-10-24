package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemNoteBlock extends ItemStack, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemNoteBlock create( int amount ) {
        return GoMint.instance().createItemStack( ItemNoteBlock.class, amount );
    }

    @Override
    default long getBurnTime() {
        return 15000;
    }

}
