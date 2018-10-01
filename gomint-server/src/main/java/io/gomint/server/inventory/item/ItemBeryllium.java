package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 270 )
public class ItemBeryllium extends ItemStack implements io.gomint.inventory.item.ItemBeryllium {

    @Override
    public String getBlockId() {
        return "minecraft:element_4";
    }

    @Override
    public ItemType getType() {
        return ItemType.BERYLLIUM;
    }
}
