package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 81 )
 public class ItemCactus extends ItemStack implements io.gomint.inventory.item.ItemCactus {



    @Override
    public String getBlockId() {
        return "minecraft:cactus";
    }

    @Override
    public ItemType getType() {
        return ItemType.CACTUS;
    }

}
