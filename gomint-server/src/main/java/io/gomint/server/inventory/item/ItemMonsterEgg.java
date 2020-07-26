package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:monster_egg", id = 97)
public class ItemMonsterEgg extends ItemStack implements io.gomint.inventory.item.ItemMonsterEgg {

    @Override
    public ItemType getItemType() {
        return ItemType.MONSTER_EGG;
    }

}
