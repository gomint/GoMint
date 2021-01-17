package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:end_rod")
public class ItemEndRod extends ItemStack< io.gomint.inventory.item.ItemEndRod> implements io.gomint.inventory.item.ItemEndRod {

    @Override
    public ItemType itemType() {
        return ItemType.END_ROD;
    }

}
