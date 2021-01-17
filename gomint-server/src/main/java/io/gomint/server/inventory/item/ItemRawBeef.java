package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:beef" )
public class ItemRawBeef extends ItemFood<io.gomint.inventory.item.ItemRawBeef> implements io.gomint.inventory.item.ItemRawBeef {



    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 3;
    }

    @Override
    public ItemType itemType() {
        return ItemType.RAW_BEEF;
    }

}
