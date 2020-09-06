package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -202, sId = "minecraft:smithing_table" )
public class ItemSmithingTable extends ItemStack implements io.gomint.inventory.item.ItemSmithingTable {

    @Override
    public ItemType getItemType() {
        return ItemType.SMITHING_TABLE;
    }

}
