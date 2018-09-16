package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 112 )
 public class ItemNetherBrickBlock extends ItemStack implements io.gomint.inventory.item.ItemNetherBrickBlock {



    @Override
    public String getBlockId() {
        return "minecraft:nether_brick";
    }

    @Override
    public ItemType getType() {
        return ItemType.NETHER_BRICK_BLOCK;
    }

}
