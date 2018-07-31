package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 132 )
 public class ItemTripwire extends ItemStack implements io.gomint.inventory.item.ItemTripwire {



    @Override
    public ItemType getType() {
        return ItemType.TRIPWIRE;
    }

}
