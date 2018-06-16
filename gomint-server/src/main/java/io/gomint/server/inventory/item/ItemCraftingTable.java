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

    // CHECKSTYLE:OFF
    public ItemCraftingTable( short data, int amount ) {
        super( 58, data, amount );
    }

    public ItemCraftingTable( short data, int amount, NBTTagCompound nbt ) {
        super( 58, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CRAFTING_TABLE;
    }

}