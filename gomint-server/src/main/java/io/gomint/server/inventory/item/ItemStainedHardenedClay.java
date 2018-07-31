package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 159 )
 public class ItemStainedHardenedClay extends ItemStack implements io.gomint.inventory.item.ItemStainedHardenedClay {



    @Override
    public ItemType getType() {
        return ItemType.STAINED_HARDENED_CLAY;
    }

}
