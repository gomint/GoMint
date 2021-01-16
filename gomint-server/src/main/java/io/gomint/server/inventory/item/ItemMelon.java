package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:melon", id = 360 )
public class ItemMelon extends ItemFood<io.gomint.inventory.item.ItemMelon> implements io.gomint.inventory.item.ItemMelon {



    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 2;
    }

    @Override
    public ItemType itemType() {
        return ItemType.MELON;
    }

}
