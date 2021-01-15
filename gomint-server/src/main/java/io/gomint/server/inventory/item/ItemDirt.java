package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dirt", id = 3)
public class ItemDirt extends ItemStack< io.gomint.inventory.item.ItemDirt> implements io.gomint.inventory.item.ItemDirt {

    @Override
    public ItemType itemType() {
        return ItemType.DIRT;
    }

}
