package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:diamond_ore", id = 56)
public class ItemDiamondOre extends ItemStack< io.gomint.inventory.item.ItemDiamondOre> implements io.gomint.inventory.item.ItemDiamondOre {

    @Override
    public ItemType itemType() {
        return ItemType.DIAMOND_ORE;
    }

}
