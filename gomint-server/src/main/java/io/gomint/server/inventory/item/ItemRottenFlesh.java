package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:rotten_flesh", id = 367 )
public class ItemRottenFlesh extends ItemFood<io.gomint.inventory.item.ItemRottenFlesh> implements io.gomint.inventory.item.ItemRottenFlesh {



    @Override
    public float getSaturation() {
        return 0.1f;
    }

    @Override
    public float getHunger() {
        return 4;
    }

    @Override
    public ItemType itemType() {
        return ItemType.ROTTEN_FLESH;
    }

}
