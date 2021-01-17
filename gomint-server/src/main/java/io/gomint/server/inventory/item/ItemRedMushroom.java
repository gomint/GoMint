package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_mushroom")
public class ItemRedMushroom extends ItemStack< io.gomint.inventory.item.ItemRedMushroom> implements io.gomint.inventory.item.ItemRedMushroom {

    @Override
    public ItemType itemType() {
        return ItemType.RED_MUSHROOM;
    }

}
