package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cooked_chicken", id = 366 )
public class ItemCookedChicken extends ItemFood implements io.gomint.inventory.item.ItemCookedChicken {



    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.COOKED_CHICKEN;
    }

}