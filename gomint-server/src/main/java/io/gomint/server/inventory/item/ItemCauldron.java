package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 380 )
public class ItemCauldron extends ItemStack implements io.gomint.inventory.item.ItemCauldron {

    @Override
    public String getBlockId() {
        return "minecraft:cauldron";
    }

    @Override
    public ItemType getType() {
        return ItemType.CAULDRON;
    }

}
