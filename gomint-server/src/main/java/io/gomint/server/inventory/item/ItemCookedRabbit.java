package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cooked_rabbit", id = 412 )
public class ItemCookedRabbit extends ItemFood implements io.gomint.inventory.item.ItemCookedRabbit {



    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 5;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.COOKED_RABBIT;
    }

}