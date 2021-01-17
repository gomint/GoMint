package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:daylight_detector")
public class ItemDaylightDetector extends ItemStack< io.gomint.inventory.item.ItemDaylightDetector> implements io.gomint.inventory.item.ItemDaylightDetector {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.DAYLIGHT_DETECTOR;
    }

}
