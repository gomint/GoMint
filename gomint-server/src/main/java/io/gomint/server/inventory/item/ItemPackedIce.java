package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:packed_ice")
public class ItemPackedIce extends ItemStack< io.gomint.inventory.item.ItemPackedIce> implements io.gomint.inventory.item.ItemPackedIce {

    @Override
    public ItemType itemType() {
        return ItemType.PACKED_ICE;
    }

}
