package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:beetroot_soup" )
public class ItemBeetrootSoup extends ItemFood<io.gomint.inventory.item.ItemBeetrootSoup> implements io.gomint.inventory.item.ItemBeetrootSoup {



    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType itemType() {
        return ItemType.BEETROOT_SOUP;
    }

}
