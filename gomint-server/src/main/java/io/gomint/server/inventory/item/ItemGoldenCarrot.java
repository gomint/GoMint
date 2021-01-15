package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:golden_carrot", id = 396 )
public class ItemGoldenCarrot extends ItemFood<io.gomint.inventory.item.ItemGoldenCarrot> implements io.gomint.inventory.item.ItemGoldenCarrot {



    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType itemType() {
        return ItemType.GOLDEN_CARROT;
    }

}
