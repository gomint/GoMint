package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:hopper", id = 410)
public class ItemHopper extends ItemStack implements io.gomint.inventory.item.ItemHopper {

    @Override
    public ItemType getType() {
        return ItemType.HOPPER;
    }

}
