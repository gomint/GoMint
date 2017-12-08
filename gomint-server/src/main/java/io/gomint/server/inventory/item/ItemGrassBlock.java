package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 2 )
 public class ItemGrassBlock extends ItemStack implements io.gomint.inventory.item.ItemGrassBlock {

    // CHECKSTYLE:OFF
    public ItemGrassBlock( short data, int amount ) {
        super( 2, data, amount );
    }

    public ItemGrassBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 2, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GRASS_BLOCK;
    }

}