package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 25 )
 public class ItemNoteBlock extends ItemStack implements io.gomint.inventory.item.ItemNoteBlock {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public String getBlockId() {
        return "minecraft:noteblock";
    }

    @Override
    public ItemType getType() {
        return ItemType.NOTE_BLOCK;
    }

}
