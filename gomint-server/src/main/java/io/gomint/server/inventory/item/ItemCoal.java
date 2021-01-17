package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coal" )
public class ItemCoal extends ItemStack<io.gomint.inventory.item.ItemCoal> implements io.gomint.inventory.item.ItemCoal {

    @Override
    public ItemType itemType() {
        return ItemType.COAL;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(80000);
    }

}
