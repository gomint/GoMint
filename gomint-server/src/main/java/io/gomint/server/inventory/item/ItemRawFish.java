package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:fish", id = 349 )
 public class ItemRawFish extends ItemStack implements io.gomint.inventory.item.ItemRawFish {



    @Override
    public ItemType getItemType() {
        return ItemType.RAW_FISH;
    }

}