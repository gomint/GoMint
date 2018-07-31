package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 405 )
 public class ItemNetherBrick extends ItemStack implements io.gomint.inventory.item.ItemNetherBrick {



    @Override
    public ItemType getType() {
        return ItemType.NETHER_BRICK;
    }

}
