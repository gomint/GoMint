package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:compass" )
 public class ItemCompass extends ItemStack< io.gomint.inventory.item.ItemCompass> implements io.gomint.inventory.item.ItemCompass {



    @Override
    public ItemType itemType() {
        return ItemType.COMPASS;
    }

}
