package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:redstone" )
 public class ItemRedstone extends ItemStack< io.gomint.inventory.item.ItemRedstone> implements io.gomint.inventory.item.ItemRedstone {



    @Override
    public ItemType itemType() {
        return ItemType.REDSTONE;
    }

}
