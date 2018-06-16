package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 271 )
 public class ItemWoodenAxe extends ItemReduceTierWooden implements io.gomint.inventory.item.ItemWoodenAxe {

    // CHECKSTYLE:OFF
    public ItemWoodenAxe( short data, int amount ) {
        super( 271, data, amount );
    }

    public ItemWoodenAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 271, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_AXE;
    }

}