package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:rabbit_hide" )
 public class ItemRabbitHide extends ItemStack< io.gomint.inventory.item.ItemRabbitHide> implements io.gomint.inventory.item.ItemRabbitHide {



    @Override
    public ItemType itemType() {
        return ItemType.RABBIT_HIDE;
    }

}
