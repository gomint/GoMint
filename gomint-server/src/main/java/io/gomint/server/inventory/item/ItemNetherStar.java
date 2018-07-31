package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 399 )
 public class ItemNetherStar extends ItemStack implements io.gomint.inventory.item.ItemNetherStar {



    @Override
    public ItemType getType() {
        return ItemType.NETHER_STAR;
    }

}
