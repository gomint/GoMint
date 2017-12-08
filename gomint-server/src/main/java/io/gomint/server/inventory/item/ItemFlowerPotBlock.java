package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 140 )
 public class ItemFlowerPotBlock extends ItemStack implements io.gomint.inventory.item.ItemFlowerPotBlock {

    // CHECKSTYLE:OFF
    public ItemFlowerPotBlock( short data, int amount ) {
        super( 140, data, amount );
    }

    public ItemFlowerPotBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 140, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FLOWER_POT_BLOCK;
    }

}