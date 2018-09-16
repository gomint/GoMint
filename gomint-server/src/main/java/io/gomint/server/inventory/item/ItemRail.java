package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 66 )
 public class ItemRail extends ItemStack implements io.gomint.inventory.item.ItemRail {



    @Override
    public String getBlockId() {
        return "minecraft:rail";
    }

    @Override
    public ItemType getType() {
        return ItemType.RAIL;
    }

}
