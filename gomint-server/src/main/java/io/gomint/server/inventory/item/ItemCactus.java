package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cactus", id = 81)
public class ItemCactus extends ItemStack implements io.gomint.inventory.item.ItemCactus {

    @Override
    public ItemType getItemType() {
        return ItemType.CACTUS;
    }

}
