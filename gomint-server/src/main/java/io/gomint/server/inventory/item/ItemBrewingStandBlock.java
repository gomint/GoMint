package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:brewing_stand", id = 117)
public class ItemBrewingStandBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.BREWING_STAND_BLOCK;
    }

}
