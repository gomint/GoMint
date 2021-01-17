package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:flower_pot", def = true)
@RegisterInfo(sId = "minecraft:item.flower_pot")
public class ItemFlowerPot extends ItemStack< io.gomint.inventory.item.ItemFlowerPot> implements io.gomint.inventory.item.ItemFlowerPot {

    @Override
    public ItemType itemType() {
        return ItemType.FLOWER_POT;
    }

}
