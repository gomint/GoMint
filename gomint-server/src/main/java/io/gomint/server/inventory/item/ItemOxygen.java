package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 274 )
public class ItemOxygen extends ItemStack implements io.gomint.inventory.item.ItemOxygen {

    @Override
    public String getBlockId() {
        return "minecraft:element_8";
    }

    @Override
    public ItemType getType() {
        return ItemType.OXYGEN;
    }
}
