package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 250 )
 public class ItemBlockMovedByPiston extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:movingBlock";
    }

    @Override
    public ItemType getType() {
        return ItemType.BLOCK_MOVED_BY_PISTON;
    }

}
