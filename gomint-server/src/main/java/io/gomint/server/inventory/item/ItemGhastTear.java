package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ghast_tear", id = 370 )
 public class ItemGhastTear extends ItemStack implements io.gomint.inventory.item.ItemGhastTear {



    @Override
    public ItemType getItemType() {
        return ItemType.GHAST_TEAR;
    }

}