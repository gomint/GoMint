package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:clownfish" )
public class ItemClownfish extends ItemFood<io.gomint.inventory.item.ItemClownfish> implements io.gomint.inventory.item.ItemClownfish {



    @Override
    public float getSaturation() {
        return 0.1f;
    }

    @Override
    public float getHunger() {
        return 1;
    }

    @Override
    public ItemType itemType() {
        return ItemType.CLOWNFISH;
    }

}
