package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 43 )
 public class ItemDoubleStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemDoubleStoneSlab {



    @Override
    public String getBlockId() {
        return "minecraft:double_stone_slab";
    }

    @Override
    public ItemType getType() {
        return ItemType.DOUBLE_STONE_SLAB;
    }

}
