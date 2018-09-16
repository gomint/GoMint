package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 117 )
 public class ItemBrewingStandBlock extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:brewing_stand";
    }

    @Override
    public ItemType getType() {
        return ItemType.BREWING_STAND_BLOCK;
    }

}
