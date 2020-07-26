package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:daylight_detector_inverted", id = 178)
public class ItemInvertedDaylightSensor extends ItemStack implements io.gomint.inventory.item.ItemInvertedDaylightSensor {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.INVERTED_DAYLIGHT_SENSOR;
    }

}
