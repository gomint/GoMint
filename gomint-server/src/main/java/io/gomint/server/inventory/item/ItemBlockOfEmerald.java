package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:emerald_block")
public class ItemBlockOfEmerald extends ItemStack< io.gomint.inventory.item.ItemBlockOfEmerald> implements io.gomint.inventory.item.ItemBlockOfEmerald {

    @Override
    public ItemType itemType() {
        return ItemType.BLOCK_OF_EMERALD;
    }

}
