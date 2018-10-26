package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 96 )
 public class ItemTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemTrapdoor {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public String getBlockId() {
        return "minecraft:trapdoor";
    }

    @Override
    public ItemType getType() {
        return ItemType.TRAPDOOR;
    }

}
