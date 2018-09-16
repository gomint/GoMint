package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 39 )
 public class ItemBrownMushroom extends ItemStack implements io.gomint.inventory.item.ItemBrownMushroom {



    @Override
    public String getBlockId() {
        return "minecraft:brown_mushroom";
    }

    @Override
    public ItemType getType() {
        return ItemType.BROWN_MUSHROOM;
    }

}
