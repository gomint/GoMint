package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 390 )
public class ItemFlowerPot extends ItemStack implements io.gomint.inventory.item.ItemFlowerPot {

    // CHECKSTYLE:OFF
    public ItemFlowerPot( short data, int amount ) {
        super( 390, data, amount );
    }

    public ItemFlowerPot( short data, int amount, NBTTagCompound nbt ) {
        super( 390, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 140;
    }

    @Override
    public ItemType getType() {
        return ItemType.FLOWER_POT;
    }

}