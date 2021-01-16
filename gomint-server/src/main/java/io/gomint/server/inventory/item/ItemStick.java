package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stick", id = 280 )
public class ItemStick extends ItemStack< io.gomint.inventory.item.ItemStick> implements io.gomint.inventory.item.ItemStick {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(5000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.STICK;
    }

}
