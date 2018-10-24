package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 173 )
 public class ItemBlockOfCoal extends ItemStack implements io.gomint.inventory.item.ItemBlockOfCoal {

    @Override
    public String getBlockId() {
        return "minecraft:coal_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.BLOCK_OF_COAL;
    }

    @Override
    public long getBurnTime() {
        return 800000;
    }

}
