package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:obsidian", id = 49)
public class ItemObsidian extends ItemStack implements io.gomint.inventory.item.ItemObsidian {

    @Override
    public ItemType getItemType() {
        return ItemType.OBSIDIAN;
    }

}
