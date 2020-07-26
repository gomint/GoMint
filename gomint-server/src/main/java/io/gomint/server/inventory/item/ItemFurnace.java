package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:furnace", id = 61)
public class ItemFurnace extends ItemStack implements io.gomint.inventory.item.ItemFurnace {

    @Override
    public ItemType getItemType() {
        return ItemType.FURNACE;
    }

}
