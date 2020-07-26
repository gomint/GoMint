package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dispenser", id = 23)
public class ItemDispenser extends ItemStack implements io.gomint.inventory.item.ItemDispenser {

    @Override
    public ItemType getItemType() {
        return ItemType.DISPENSER;
    }

}
