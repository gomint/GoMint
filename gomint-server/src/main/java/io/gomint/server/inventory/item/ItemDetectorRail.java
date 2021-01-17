package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:detector_rail")
public class ItemDetectorRail extends ItemStack< io.gomint.inventory.item.ItemDetectorRail> implements io.gomint.inventory.item.ItemDetectorRail {

    @Override
    public ItemType itemType() {
        return ItemType.DETECTOR_RAIL;
    }

}
