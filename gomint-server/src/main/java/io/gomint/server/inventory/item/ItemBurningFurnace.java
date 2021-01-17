package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lit_furnace")
public class ItemBurningFurnace extends ItemStack< io.gomint.inventory.item.ItemBurningFurnace> implements io.gomint.inventory.item.ItemBurningFurnace {

    @Override
    public ItemType itemType() {
        return ItemType.BURNING_FURNACE;
    }

}
