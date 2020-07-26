package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sand", id = 12)
public class ItemSand extends ItemStack implements io.gomint.inventory.item.ItemSand {

    @Override
    public ItemType getItemType() {
        return ItemType.SAND;
    }

}
