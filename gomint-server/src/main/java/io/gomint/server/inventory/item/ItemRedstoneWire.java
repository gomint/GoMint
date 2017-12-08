package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 55 )
public class ItemRedstoneWire extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemRedstoneWire( short data, int amount ) {
        super( 55, data, amount );
    }

    public ItemRedstoneWire( short data, int amount, NBTTagCompound nbt ) {
        super( 55, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_WIRE;
    }

}
