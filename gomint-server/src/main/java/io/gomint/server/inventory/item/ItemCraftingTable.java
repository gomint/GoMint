package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 58 )
 public class ItemCraftingTable extends ItemStack implements io.gomint.inventory.item.ItemCraftingTable {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public String getBlockId() {
        return "minecraft:crafting_table";
    }

    @Override
    public ItemType getType() {
        return ItemType.CRAFTING_TABLE;
    }

}
