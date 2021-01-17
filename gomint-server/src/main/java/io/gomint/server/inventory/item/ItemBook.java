package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:book" )
public class ItemBook extends ItemStack< io.gomint.inventory.item.ItemBook> implements io.gomint.inventory.item.ItemBook {

    @Override
    public ItemType itemType() {
        return ItemType.BOOK;
    }

    @Override
    public int enchantAbility() {
        return 1;
    }

}
