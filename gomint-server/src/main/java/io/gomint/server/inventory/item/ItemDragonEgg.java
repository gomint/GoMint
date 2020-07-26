package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dragon_egg", id = 122)
public class ItemDragonEgg extends ItemStack implements io.gomint.inventory.item.ItemDragonEgg {

    @Override
    public ItemType getItemType() {
        return ItemType.DRAGON_EGG;
    }

}
