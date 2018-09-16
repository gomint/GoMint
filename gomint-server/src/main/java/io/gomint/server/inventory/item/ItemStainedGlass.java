package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 241 )
 public class ItemStainedGlass extends ItemStack implements io.gomint.inventory.item.ItemStainedGlass {



    @Override
    public String getBlockId() {
        return "minecraft:stained_glass";
    }

    @Override
    public ItemType getType() {
        return ItemType.STAINED_GLASS;
    }

}
