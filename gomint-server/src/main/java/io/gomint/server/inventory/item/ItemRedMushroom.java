package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_mushroom", id = 40)
public class ItemRedMushroom extends ItemStack implements io.gomint.inventory.item.ItemRedMushroom {

    @Override
    public ItemType getItemType() {
        return ItemType.RED_MUSHROOM;
    }

}
