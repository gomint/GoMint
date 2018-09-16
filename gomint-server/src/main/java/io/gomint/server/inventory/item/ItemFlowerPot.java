package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 390 )
public class ItemFlowerPot extends ItemStack implements io.gomint.inventory.item.ItemFlowerPot {

    @Override
    public String getBlockId() {
        return "minecraft:flower_pot";
    }

    @Override
    public ItemType getType() {
        return ItemType.FLOWER_POT;
    }

}
