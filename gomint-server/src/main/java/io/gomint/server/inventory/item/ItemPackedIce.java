package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:packed_ice", id = 174)
public class ItemPackedIce extends ItemStack implements io.gomint.inventory.item.ItemPackedIce {

    @Override
    public ItemType getItemType() {
        return ItemType.PACKED_ICE;
    }

}
