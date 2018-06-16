package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 34 )
 public class ItemPistonHead extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemPistonHead( short data, int amount ) {
        super( 34, data, amount );
    }

    public ItemPistonHead( short data, int amount, NBTTagCompound nbt ) {
        super( 34, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.PISTON_HEAD;
    }

}
