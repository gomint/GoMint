package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 430 )
public class ItemAcaciaWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemAcaciaWoodDoor {

    // CHECKSTYLE:OFF
    public ItemAcaciaWoodDoor( short data, int amount ) {
        super( 430, data, amount );
    }

    public ItemAcaciaWoodDoor( short data, int amount, NBTTagCompound nbt ) {
        super( 430, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 196;
    }

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_DOOR;
    }

}
