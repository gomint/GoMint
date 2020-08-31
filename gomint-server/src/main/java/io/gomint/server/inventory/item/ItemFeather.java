package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:feather", id = 288 )
 public class ItemFeather extends ItemStack implements io.gomint.inventory.item.ItemFeather {



    @Override
    public ItemType getItemType() {
        return ItemType.FEATHER;
    }

}