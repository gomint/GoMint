package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 86 )
 public class ItemPumpkin extends ItemStack implements io.gomint.inventory.item.ItemPumpkin {



    @Override
    public String getBlockId() {
        return "minecraft:pumpkin";
    }

    @Override
    public ItemType getType() {
        return ItemType.PUMPKIN;
    }

}
