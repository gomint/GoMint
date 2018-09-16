package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 4 )
 public class ItemCobblestone extends ItemStack implements io.gomint.inventory.item.ItemCobblestone {



    @Override
    public String getBlockId() {
        return "minecraft:cobblestone";
    }

    @Override
    public ItemType getType() {
        return ItemType.COBBLESTONE;
    }

}
