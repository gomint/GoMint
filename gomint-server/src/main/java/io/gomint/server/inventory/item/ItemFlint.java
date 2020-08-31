package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:flint", id = 318 )
 public class ItemFlint extends ItemStack implements io.gomint.inventory.item.ItemFlint {



    @Override
    public ItemType getItemType() {
        return ItemType.FLINT;
    }

}