package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pumpkin_pie", id = 400 )
public class ItemPumpkinPie extends ItemFood<io.gomint.inventory.item.ItemPumpkinPie> implements io.gomint.inventory.item.ItemPumpkinPie {



    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 8;
    }

    @Override
    public ItemType itemType() {
        return ItemType.PUMPKIN_PIE;
    }

}
