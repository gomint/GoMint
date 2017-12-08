package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 45 )
 public class ItemBrickBlock extends ItemStack implements io.gomint.inventory.item.ItemBrickBlock {

    // CHECKSTYLE:OFF
    public ItemBrickBlock( short data, int amount ) {
        super( 45, data, amount );
    }

    public ItemBrickBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 45, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BRICK_BLOCK;
    }

}