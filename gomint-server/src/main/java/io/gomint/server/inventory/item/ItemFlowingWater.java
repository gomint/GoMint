package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:flowing_water", id = 8)
public class ItemFlowingWater extends ItemStack implements io.gomint.inventory.item.ItemFlowingWater {

    @Override
    public ItemType getItemType() {
        return ItemType.FLOWING_WATER;
    }

}
