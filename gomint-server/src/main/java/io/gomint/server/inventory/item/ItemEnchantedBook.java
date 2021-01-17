package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:enchanted_book" )
 public class ItemEnchantedBook extends ItemStack< io.gomint.inventory.item.ItemEnchantedBook> implements io.gomint.inventory.item.ItemEnchantedBook {



    @Override
    public ItemType itemType() {
        return ItemType.ENCHANTED_BOOK;
    }

}
