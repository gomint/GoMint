package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 427 )
public class ItemSpruceDoor extends ItemStack implements io.gomint.inventory.item.ItemSpruceDoor {

    // CHECKSTYLE:OFF
    public ItemSpruceDoor( short data, int amount ) {
        super( 427, data, amount );
    }

    public ItemSpruceDoor( short data, int amount, NBTTagCompound nbt ) {
        super( 427, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 193;
    }
    @Override
    public ItemType getType() {
        return ItemType.SPRUCE_DOOR;
    }

}