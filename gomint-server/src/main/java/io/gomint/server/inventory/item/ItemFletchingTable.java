package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -201, sId = "minecraft:fletching_table")
public class ItemFletchingTable extends ItemStack implements io.gomint.inventory.item.ItemFletchingTable {

    @Override
    public String getBlockId() {
        return "minecraft:fletching_table";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.FLETCHING_TABLE;
    }

}
