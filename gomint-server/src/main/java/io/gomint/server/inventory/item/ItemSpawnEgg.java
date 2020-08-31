package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:spawn_egg", id = 383 )
public class ItemSpawnEgg extends ItemStack implements io.gomint.inventory.item.ItemSpawnEgg {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SPAWN_EGG;
    }

}
