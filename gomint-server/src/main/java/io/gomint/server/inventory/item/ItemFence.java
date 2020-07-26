package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fence", id = 85)
public class ItemFence extends ItemStack implements io.gomint.inventory.item.ItemFence {

    @Override
    public ItemType getItemType() {
        return ItemType.FENCE;
    }

}
