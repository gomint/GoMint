package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 385 )
public class ItemFireCharge extends ItemStack implements io.gomint.inventory.item.ItemFireCharge {

    // CHECKSTYLE:OFF
    public ItemFireCharge( short data, int amount ) {
        super( 385, data, amount );
    }

    public ItemFireCharge( short data, int amount, NBTTagCompound nbt ) {
        super( 385, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FIRE_CHARGE;
    }

}
