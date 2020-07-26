package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:observer", id = 251)
public class ItemObserver extends ItemStack implements io.gomint.inventory.item.ItemObserver {

    @Override
    public ItemType getItemType() {
        return ItemType.OBSERVER;
    }

}
