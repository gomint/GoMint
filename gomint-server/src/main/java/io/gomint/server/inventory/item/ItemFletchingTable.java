package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:fletching_table")
public class ItemFletchingTable extends ItemStack< io.gomint.inventory.item.ItemFletchingTable> implements io.gomint.inventory.item.ItemFletchingTable {

    @Override
    public ItemType itemType() {
        return ItemType.FLETCHING_TABLE;
    }

}
