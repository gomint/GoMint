package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:rabbit" )
public class ItemRawRabbit extends ItemFood<io.gomint.inventory.item.ItemRawRabbit> implements io.gomint.inventory.item.ItemRawRabbit {



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
        return ItemType.RAW_RABBIT;
    }

}
