package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:water")
public class ItemStationaryWater extends ItemStack< io.gomint.inventory.item.ItemStationaryWater> implements io.gomint.inventory.item.ItemStationaryWater {

    @Override
    public ItemType itemType() {
        return ItemType.STATIONARY_WATER;
    }

}
