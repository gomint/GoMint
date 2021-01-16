package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sandstone_stairs", id = 128)
public class ItemSandstoneStairs extends ItemStack< io.gomint.inventory.item.ItemSandstoneStairs> implements io.gomint.inventory.item.ItemSandstoneStairs {

    @Override
    public ItemType itemType() {
        return ItemType.SANDSTONE_STAIRS;
    }

}
