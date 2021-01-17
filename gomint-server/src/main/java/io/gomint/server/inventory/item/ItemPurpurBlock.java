package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:purpur_block")
public class ItemPurpurBlock extends ItemStack< io.gomint.inventory.item.ItemPurpurBlock> implements io.gomint.inventory.item.ItemPurpurBlock {

    @Override
    public ItemType itemType() {
        return ItemType.PURPUR_BLOCK;
    }

}
