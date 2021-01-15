package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:boat", id = 333 )
public class ItemBoat extends ItemStack< io.gomint.inventory.item.ItemBoat> implements io.gomint.inventory.item.ItemBoat {

    @Override
    public ItemType itemType() {
        return ItemType.BOAT;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(60000);
    }

}
