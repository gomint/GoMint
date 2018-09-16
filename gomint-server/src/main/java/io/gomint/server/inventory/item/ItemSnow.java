package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 80 )
 public class ItemSnow extends ItemStack implements io.gomint.inventory.item.ItemSnow {



    @Override
    public String getBlockId() {
        return "minecraft:snow";
    }

    @Override
    public ItemType getType() {
        return ItemType.SNOW;
    }

}
