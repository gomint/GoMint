package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 97 )
 public class ItemMonsterEgg extends ItemStack implements io.gomint.inventory.item.ItemMonsterEgg {



    @Override
    public String getBlockId() {
        return "minecraft:monster_egg";
    }

    @Override
    public ItemType getType() {
        return ItemType.MONSTER_EGG;
    }

}
