package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dropper", id = 125)
public class ItemDropper extends ItemStack implements io.gomint.inventory.item.ItemDropper {

    @Override
    public ItemType getItemType() {
        return ItemType.DROPPER;
    }

}
