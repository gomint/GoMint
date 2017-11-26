package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 52 )
 public class ItemMobSpawner extends ItemStack implements io.gomint.inventory.item.ItemMobSpawner {

    // CHECKSTYLE:OFF
    public ItemMobSpawner( short data, int amount ) {
        super( 52, data, amount );
    }

    public ItemMobSpawner( short data, int amount, NBTTagCompound nbt ) {
        super( 52, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.MOB_SPAWNER;
    }

}