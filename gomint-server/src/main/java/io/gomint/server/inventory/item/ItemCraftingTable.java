package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:crafting_table")
public class ItemCraftingTable extends ItemStack< io.gomint.inventory.item.ItemCraftingTable> implements io.gomint.inventory.item.ItemCraftingTable {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.CRAFTING_TABLE;
    }

}
