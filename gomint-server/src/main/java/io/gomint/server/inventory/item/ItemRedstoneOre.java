package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:redstone_ore", id = 73)
public class ItemRedstoneOre extends ItemStack implements io.gomint.inventory.item.ItemRedstoneOre {

    @Override
    public ItemType getItemType() {
        return ItemType.REDSTONE_ORE;
    }

}
