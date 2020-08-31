package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cookie", id = 357 )
public class ItemCookie extends ItemFood implements io.gomint.inventory.item.ItemCookie {



    @Override
    public float getSaturation() {
        return 0.1f;
    }

    @Override
    public float getHunger() {
        return 2;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.COOKIE;
    }

}