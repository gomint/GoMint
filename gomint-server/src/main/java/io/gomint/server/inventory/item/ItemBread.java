package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bread" )
public class ItemBread extends ItemFood<io.gomint.inventory.item.ItemBread> implements io.gomint.inventory.item.ItemBread {



    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 5;
    }

    @Override
    public ItemType itemType() {
        return ItemType.BREAD;
    }

}
