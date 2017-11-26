package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 198 )
 public class ItemGrassPath extends ItemStack implements io.gomint.inventory.item.ItemGrassPath {

    // CHECKSTYLE:OFF
    public ItemGrassPath( short data, int amount ) {
        super( 198, data, amount );
    }

    public ItemGrassPath( short data, int amount, NBTTagCompound nbt ) {
        super( 198, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GRASS_PATH;
    }

}