package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 157 )
 public class ItemWoodenDoubleSlab extends ItemStack implements io.gomint.inventory.item.ItemWoodenDoubleSlab {



    @Override
    public String getBlockId() {
        return "minecraft:double_wooden_slab";
    }

    @Override
    public ItemType getType() {
        return ItemType.WOODEN_DOUBLE_SLAB;
    }

}
