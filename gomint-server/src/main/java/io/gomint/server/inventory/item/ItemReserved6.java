package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 255 )
public class ItemReserved6 extends ItemStack {
    
    @Override
    public String getBlockId() {
        return "minecraft:reserved6";
    }

    @Override
    public ItemType getType() {
        return ItemType.RESERVED6;
    }

}
