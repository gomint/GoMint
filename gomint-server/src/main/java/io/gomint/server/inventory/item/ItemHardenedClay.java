package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:hardened_clay", id = 172)
public class ItemHardenedClay extends ItemStack< io.gomint.inventory.item.ItemHardenedClay> implements io.gomint.inventory.item.ItemHardenedClay {

    @Override
    public ItemType itemType() {
        return ItemType.HARDENED_CLAY;
    }

}
