package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 23 )
 public class ItemDispenser extends ItemStack implements io.gomint.inventory.item.ItemDispenser {



    @Override
    public String getBlockId() {
        return "minecraft:dispenser";
    }

    @Override
    public ItemType getType() {
        return ItemType.DISPENSER;
    }

}
