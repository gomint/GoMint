package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 271 )
public class ItemBoron extends ItemStack implements io.gomint.inventory.item.ItemBoron {

    @Override
    public String getBlockId() {
        return "minecraft:element_5";
    }

    @Override
    public ItemType getType() {
        return ItemType.BORON;
    }
}
