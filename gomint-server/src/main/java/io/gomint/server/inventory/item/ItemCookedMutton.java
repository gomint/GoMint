package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:muttoncooked", id = 424 )
public class ItemCookedMutton extends ItemFood implements io.gomint.inventory.item.ItemCookedMutton {



    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.COOKED_MUTTON;
    }

}