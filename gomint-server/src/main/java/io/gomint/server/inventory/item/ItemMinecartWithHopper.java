package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:hopper_minecart", id = 408 )
 public class ItemMinecartWithHopper extends ItemStack implements io.gomint.inventory.item.ItemMinecartWithHopper {



    @Override
    public ItemType getItemType() {
        return ItemType.MINECART_WITH_HOPPER;
    }

}