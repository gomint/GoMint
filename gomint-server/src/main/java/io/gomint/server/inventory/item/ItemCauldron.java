package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 380 )
public class ItemCauldron extends ItemStack implements io.gomint.inventory.item.ItemCauldron {



    @Override
    public int getBlockId() {
        return 118;
    }

    @Override
    public ItemType getType() {
        return ItemType.CAULDRON;
    }

}
