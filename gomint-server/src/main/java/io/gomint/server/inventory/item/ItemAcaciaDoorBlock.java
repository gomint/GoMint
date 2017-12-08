package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 196 )
public class ItemAcaciaDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemAcaciaDoorBlock( short data, int amount ) {
        super( 196, data, amount );
    }

    public ItemAcaciaDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 196, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_DOOR_BLOCK;
    }

}