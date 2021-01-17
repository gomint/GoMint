package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lectern")
public class ItemLectern extends ItemStack< io.gomint.inventory.item.ItemLectern> implements io.gomint.inventory.item.ItemLectern {

    @Override
    public ItemType itemType() {
        return ItemType.LECTERN;
    }

}
