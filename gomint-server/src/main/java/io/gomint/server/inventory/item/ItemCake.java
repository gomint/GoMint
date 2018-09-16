package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 354 )
public class ItemCake extends ItemStack implements io.gomint.inventory.item.ItemCake {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public String getBlockId() {
        return "minecraft:cake";
    }

    @Override
    public ItemType getType() {
        return ItemType.CAKE;
    }

}
