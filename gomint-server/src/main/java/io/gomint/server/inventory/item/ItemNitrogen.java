package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 273 )
public class ItemNitrogen extends ItemStack {

    @Override
    public String getBlockId() {
        return "minecraft:element_7";
    }

    @Override
    public ItemType getType() {
        return ItemType.NITROGEN;
    }
}
