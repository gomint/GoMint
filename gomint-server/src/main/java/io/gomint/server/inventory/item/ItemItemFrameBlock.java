package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:frame", id = 199)
public class ItemItemFrameBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.ITEM_FRAME_BLOCK;
    }

}
