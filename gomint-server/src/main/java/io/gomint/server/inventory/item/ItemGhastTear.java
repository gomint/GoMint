package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ghast_tear" )
 public class ItemGhastTear extends ItemStack< io.gomint.inventory.item.ItemGhastTear> implements io.gomint.inventory.item.ItemGhastTear {



    @Override
    public ItemType itemType() {
        return ItemType.GHAST_TEAR;
    }

}
