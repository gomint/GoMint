package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:blaze_rod" )
public class ItemBlazeRod extends ItemStack< io.gomint.inventory.item.ItemBlazeRod> implements io.gomint.inventory.item.ItemBlazeRod {

    @Override
    public ItemType itemType() {
        return ItemType.BLAZE_ROD;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(120000);
    }

}
