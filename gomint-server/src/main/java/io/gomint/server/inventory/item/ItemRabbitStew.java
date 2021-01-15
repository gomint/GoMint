package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:rabbit_stew", id = 413 )
public class ItemRabbitStew extends ItemFood<io.gomint.inventory.item.ItemRabbitStew> implements io.gomint.inventory.item.ItemRabbitStew {



    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 10;
    }

    @Override
    public ItemType itemType() {
        return ItemType.RABBIT_STEW;
    }

}
