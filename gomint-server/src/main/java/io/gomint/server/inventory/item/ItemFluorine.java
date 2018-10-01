package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 275 )
public class ItemFluorine extends ItemStack implements io.gomint.inventory.item.ItemFluorine {

    @Override
    public String getBlockId() {
        return "minecraft:element_9";
    }

    @Override
    public ItemType getType() {
        return ItemType.FLUORINE;
    }
}
