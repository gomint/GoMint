package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:saddle", id = 329 )
 public class ItemSaddle extends ItemStack implements io.gomint.inventory.item.ItemSaddle {



    @Override
    public ItemType getItemType() {
        return ItemType.SADDLE;
    }

}