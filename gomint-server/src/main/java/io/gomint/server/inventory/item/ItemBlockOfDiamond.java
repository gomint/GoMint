package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:diamond_block", id = 57)
public class ItemBlockOfDiamond extends ItemStack< io.gomint.inventory.item.ItemBlockOfDiamond> implements io.gomint.inventory.item.ItemBlockOfDiamond {

    @Override
    public ItemType itemType() {
        return ItemType.BLOCK_OF_DIAMOND;
    }

}
