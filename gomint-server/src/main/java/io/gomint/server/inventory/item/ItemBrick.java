package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:brick", id = 336 )
 public class ItemBrick extends ItemStack< io.gomint.inventory.item.ItemBrick> implements io.gomint.inventory.item.ItemBrick {



    @Override
    public ItemType itemType() {
        return ItemType.BRICK;
    }

}
