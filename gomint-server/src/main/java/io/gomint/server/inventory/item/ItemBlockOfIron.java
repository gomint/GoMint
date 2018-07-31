package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 42 )
 public class ItemBlockOfIron extends ItemStack implements io.gomint.inventory.item.ItemBlockOfIron {



    @Override
    public ItemType getType() {
        return ItemType.BLOCK_OF_IRON;
    }

}
