package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:written_book" )
public class ItemWrittenBook extends ItemStack< io.gomint.inventory.item.ItemWrittenBook> implements io.gomint.inventory.item.ItemWrittenBook {



    @Override
    public ItemType itemType() {
        return ItemType.WRITTEN_BOOK;
    }

}
