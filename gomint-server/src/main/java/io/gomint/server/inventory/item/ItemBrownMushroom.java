package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:brown_mushroom")
public class ItemBrownMushroom extends ItemStack< io.gomint.inventory.item.ItemBrownMushroom> implements io.gomint.inventory.item.ItemBrownMushroom {

    @Override
    public ItemType itemType() {
        return ItemType.BROWN_MUSHROOM;
    }

}
