package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 13 )
 public class ItemGravel extends ItemStack implements io.gomint.inventory.item.ItemGravel {

    // CHECKSTYLE:OFF
    public ItemGravel( short data, int amount ) {
        super( 13, data, amount );
    }

    public ItemGravel( short data, int amount, NBTTagCompound nbt ) {
        super( 13, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GRAVEL;
    }

}