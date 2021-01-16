package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coal_ore", id = 16)
public class ItemCoalOre extends ItemStack< io.gomint.inventory.item.ItemCoalOre> implements io.gomint.inventory.item.ItemCoalOre {

    @Override
    public ItemType itemType() {
        return ItemType.COAL_ORE;
    }

}
