package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:daylight_detector_inverted", id = 178)
public class ItemInvertedDaylightSensor extends ItemStack< io.gomint.inventory.item.ItemInvertedDaylightSensor> implements io.gomint.inventory.item.ItemInvertedDaylightSensor {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.INVERTED_DAYLIGHT_SENSOR;
    }

}
