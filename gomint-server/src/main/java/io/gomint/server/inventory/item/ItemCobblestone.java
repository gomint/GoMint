package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone", id = 4)
public class ItemCobblestone extends ItemStack implements io.gomint.inventory.item.ItemCobblestone {

    @Override
    public ItemType getItemType() {
        return ItemType.COBBLESTONE;
    }

}
