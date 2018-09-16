package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 181 )
 public class ItemDoubleRedSandstoneSlab extends ItemStack implements io.gomint.inventory.item.ItemDoubleRedSandstoneSlab {



    @Override
    public String getBlockId() {
        return "minecraft:double_stone_slab2";
    }

    @Override
    public ItemType getType() {
        return ItemType.DOUBLE_RED_SANDSTONE_SLAB;
    }

}
