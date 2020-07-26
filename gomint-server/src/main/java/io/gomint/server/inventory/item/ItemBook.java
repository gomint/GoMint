package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 340 )
public class ItemBook extends ItemStack implements io.gomint.inventory.item.ItemBook {

    @Override
    public ItemType getItemType() {
        return ItemType.BOOK;
    }

    @Override
    public int getEnchantAbility() {
        return 1;
    }

}
