package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 71 )
public class ItemIronDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemIronDoorBlock( short data, int amount ) {
        super( 71, data, amount );
    }

    public ItemIronDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 71, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.IRON_DOOR_BLOCK;
    }

}