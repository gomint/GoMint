package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cauldron", id = 118)
public class ItemCauldronBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.CAULDRON_BLOCK;
    }

}
