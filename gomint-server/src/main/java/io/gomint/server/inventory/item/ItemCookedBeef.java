package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cooked_beef", id = 364 )
public class ItemCookedBeef extends ItemFood<io.gomint.inventory.item.ItemCookedBeef> implements io.gomint.inventory.item.ItemCookedBeef {



    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public float getHunger() {
        return 8f;
    }

    @Override
    public ItemType itemType() {
        return ItemType.COOKED_BEEF;
    }

}
