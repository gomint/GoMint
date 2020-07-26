package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = -196, sId = "minecraft:blast_furnace")
public class ItemBlastFurnace extends ItemStack implements io.gomint.inventory.item.ItemBlastFurnace {

    @Override
    public String getBlockId() {
        return "minecraft:blast_furnace";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.BLAST_FURNACE;
    }

}
