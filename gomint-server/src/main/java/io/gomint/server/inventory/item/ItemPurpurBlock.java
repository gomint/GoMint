package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:purpur_block", id = 201)
public class ItemPurpurBlock extends ItemStack implements io.gomint.inventory.item.ItemPurpurBlock {

    @Override
    public ItemType getItemType() {
        return ItemType.PURPUR_BLOCK;
    }

}
