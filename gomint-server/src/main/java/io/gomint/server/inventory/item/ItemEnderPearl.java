package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 368 )
 public class ItemEnderPearl extends ItemStack implements io.gomint.inventory.item.ItemEnderPearl {

    // CHECKSTYLE:OFF
    public ItemEnderPearl( short data, int amount ) {
        super( 368, data, amount );
    }

    public ItemEnderPearl( short data, int amount, NBTTagCompound nbt ) {
        super( 368, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ENDER_PEARL;
    }

}