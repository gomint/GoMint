package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:apple", id = 260 )
public class ItemApple extends ItemFood implements io.gomint.inventory.item.ItemApple {



    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 4;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.APPLE;
    }

}