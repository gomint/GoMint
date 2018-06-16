package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 383 )
public class ItemSpawnEgg extends ItemStack implements io.gomint.inventory.item.ItemSpawnEgg {

    // CHECKSTYLE:OFF
    public ItemSpawnEgg( short data, int amount ) {
        super( 383, data, amount );
    }

    public ItemSpawnEgg( short data, int amount, NBTTagCompound nbt ) {
        super( 383, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public ItemType getType() {
        return ItemType.SPAWN_EGG;
    }

}