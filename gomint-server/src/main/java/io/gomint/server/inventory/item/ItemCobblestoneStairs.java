package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_stairs")
public class ItemCobblestoneStairs extends ItemStack< io.gomint.inventory.item.ItemCobblestoneStairs> implements io.gomint.inventory.item.ItemCobblestoneStairs {

    @Override
    public ItemType itemType() {
        return ItemType.COBBLESTONE_STAIRS;
    }

}
