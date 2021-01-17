package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:blast_furnace")
public class ItemBlastFurnace extends ItemStack< io.gomint.inventory.item.ItemBlastFurnace> implements io.gomint.inventory.item.ItemBlastFurnace {

    @Override
    public ItemType itemType() {
        return ItemType.BLAST_FURNACE;
    }

}
