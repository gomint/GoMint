package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = -195, sId = "minecraft:grindstone")
public class ItemGrindstone extends ItemStack< io.gomint.inventory.item.ItemGrindstone> implements io.gomint.inventory.item.ItemGrindstone {

    @Override
    public ItemType itemType() {
        return ItemType.GRINDSTONE;
    }

}
