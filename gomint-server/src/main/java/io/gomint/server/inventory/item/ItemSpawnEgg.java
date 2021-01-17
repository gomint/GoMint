package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:spawn_egg" )
public class ItemSpawnEgg extends ItemStack< io.gomint.inventory.item.ItemSpawnEgg> implements io.gomint.inventory.item.ItemSpawnEgg {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public ItemType itemType() {
        return ItemType.SPAWN_EGG;
    }

}
