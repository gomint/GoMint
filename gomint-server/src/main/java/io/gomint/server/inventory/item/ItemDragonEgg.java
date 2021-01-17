package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dragon_egg")
public class ItemDragonEgg extends ItemStack< io.gomint.inventory.item.ItemDragonEgg> implements io.gomint.inventory.item.ItemDragonEgg {

    @Override
    public ItemType itemType() {
        return ItemType.DRAGON_EGG;
    }

}
