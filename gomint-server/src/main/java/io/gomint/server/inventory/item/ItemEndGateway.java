package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 209 )
 public class ItemEndGateway extends ItemStack implements io.gomint.inventory.item.ItemEndGateway {

    // CHECKSTYLE:OFF
    public ItemEndGateway( short data, int amount ) {
        super( 209, data, amount );
    }

    public ItemEndGateway( short data, int amount, NBTTagCompound nbt ) {
        super( 209, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.END_GATEWAY;
    }

}