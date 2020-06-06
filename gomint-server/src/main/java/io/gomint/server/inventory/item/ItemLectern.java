package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -194, sId = "minecraft:lectern")
public class ItemLectern extends ItemStack implements io.gomint.inventory.item.ItemLectern {

    @Override
    public ItemType getType() {
        return ItemType.LECTERN;
    }

    @Override
    public String getBlockId() {
        return "minecraft:lectern";
    }

}
