package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_sandstone_stairs", id = 180)
public class ItemRedSandstoneStairs extends ItemStack< io.gomint.inventory.item.ItemRedSandstoneStairs> implements io.gomint.inventory.item.ItemRedSandstoneStairs {

    @Override
    public ItemType itemType() {
        return ItemType.RED_SANDSTONE_STAIRS;
    }

}
