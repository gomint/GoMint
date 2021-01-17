package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:slime")
public class ItemSlimeBlock extends ItemStack< io.gomint.inventory.item.ItemSlimeBlock> implements io.gomint.inventory.item.ItemSlimeBlock {

    @Override
    public ItemType itemType() {
        return ItemType.SLIME_BLOCK;
    }

}
