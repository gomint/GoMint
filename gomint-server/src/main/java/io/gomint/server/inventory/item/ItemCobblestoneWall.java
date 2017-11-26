package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 139 )
 public class ItemCobblestoneWall extends ItemStack implements io.gomint.inventory.item.ItemCobblestoneWall {

    // CHECKSTYLE:OFF
    public ItemCobblestoneWall( short data, int amount ) {
        super( 139, data, amount );
    }

    public ItemCobblestoneWall( short data, int amount, NBTTagCompound nbt ) {
        super( 139, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.COBBLESTONE_WALL;
    }

}