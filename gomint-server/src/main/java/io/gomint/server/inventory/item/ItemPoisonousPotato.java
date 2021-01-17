package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:poisonous_potato" )
public class ItemPoisonousPotato extends ItemFood<io.gomint.inventory.item.ItemPoisonousPotato> implements io.gomint.inventory.item.ItemPoisonousPotato {



    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 2;
    }

    @Override
    public ItemType itemType() {
        return ItemType.POISONOUS_POTATO;
    }

}
