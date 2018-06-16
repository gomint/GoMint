package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 26 )
 public class ItemBedBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemBedBlock( short data, int amount ) {
        super( 26, data, amount );
    }

    public ItemBedBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 26, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BED_BLOCK;
    }

}