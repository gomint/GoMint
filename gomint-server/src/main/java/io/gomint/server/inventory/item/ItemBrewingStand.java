package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 379 )
public class ItemBrewingStand extends ItemStack implements io.gomint.inventory.item.ItemBrewingStand {

    @Override
    public String getBlockId() {
        return "minecraft:brewing_stand";
    }

    @Override
    public ItemType getType() {
        return ItemType.BREWING_STAND;
    }

}
