package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 104 )
 public class ItemPumpkinStem extends ItemStack implements io.gomint.inventory.item.ItemPumpkinStem {



    @Override
    public String getBlockId() {
        return "minecraft:pumpkin_stem";
    }

    @Override
    public ItemType getType() {
        return ItemType.PUMPKIN_STEM;
    }

}
