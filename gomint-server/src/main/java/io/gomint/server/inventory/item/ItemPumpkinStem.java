package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:pumpkin_stem")
public class ItemPumpkinStem extends ItemStack< io.gomint.inventory.item.ItemPumpkinStem> implements io.gomint.inventory.item.ItemPumpkinStem {

    @Override
    public ItemType itemType() {
        return ItemType.PUMPKIN_STEM;
    }

}
