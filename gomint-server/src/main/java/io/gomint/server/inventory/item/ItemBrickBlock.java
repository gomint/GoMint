package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 45 )
 public class ItemBrickBlock extends ItemStack implements io.gomint.inventory.item.ItemBrickBlock {



    @Override
    public String getBlockId() {
        return "minecraft:brick_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.BRICK_BLOCK;
    }

}
