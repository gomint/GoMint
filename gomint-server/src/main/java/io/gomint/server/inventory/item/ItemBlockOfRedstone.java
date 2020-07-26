package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:redstone_block", id = 152)
public class ItemBlockOfRedstone extends ItemStack implements io.gomint.inventory.item.ItemBlockOfRedstone {

    @Override
    public ItemType getItemType() {
        return ItemType.BLOCK_OF_REDSTONE;
    }

}
