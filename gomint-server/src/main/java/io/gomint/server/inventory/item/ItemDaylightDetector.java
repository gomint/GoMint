package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:daylight_detector", id = 151)
public class ItemDaylightDetector extends ItemStack implements io.gomint.inventory.item.ItemDaylightDetector {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.DAYLIGHT_DETECTOR;
    }

}
