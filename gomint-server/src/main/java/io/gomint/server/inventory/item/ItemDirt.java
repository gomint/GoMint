package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 3 )
 public class ItemDirt extends ItemStack implements io.gomint.inventory.item.ItemDirt {



    @Override
    public String getBlockId() {
        return "minecraft:dirt";
    }

    @Override
    public ItemType getType() {
        return ItemType.DIRT;
    }

}
