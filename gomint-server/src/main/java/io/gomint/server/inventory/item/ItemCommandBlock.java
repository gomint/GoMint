package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 137 )
public class ItemCommandBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemCommandBlock( short data, int amount ) {
        super( 137, data, amount );
    }

    public ItemCommandBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 137, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.COMMAND_BLOCK;
    }

}