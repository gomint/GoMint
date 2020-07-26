package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coal_block", id = 173)
public class ItemBlockOfCoal extends ItemStack implements io.gomint.inventory.item.ItemBlockOfCoal {

    @Override
    public ItemType getItemType() {
        return ItemType.BLOCK_OF_COAL;
    }

    @Override
    public long getBurnTime() {
        return 800000;
    }

}
