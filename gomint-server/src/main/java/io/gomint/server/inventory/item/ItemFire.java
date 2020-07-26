package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fire", id = 51)
public class ItemFire extends ItemStack implements io.gomint.inventory.item.ItemFire {

    @Override
    public ItemType getItemType() {
        return ItemType.FIRE;
    }

}
