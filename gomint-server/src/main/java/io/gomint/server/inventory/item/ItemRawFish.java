package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:fish" )
 public class ItemRawFish extends ItemStack< io.gomint.inventory.item.ItemRawFish> implements io.gomint.inventory.item.ItemRawFish {



    @Override
    public ItemType itemType() {
        return ItemType.RAW_FISH;
    }

}
