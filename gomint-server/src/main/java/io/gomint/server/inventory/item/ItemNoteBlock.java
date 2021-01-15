package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:noteblock", id = 25)
public class ItemNoteBlock extends ItemStack< io.gomint.inventory.item.ItemNoteBlock> implements io.gomint.inventory.item.ItemNoteBlock {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.NOTE_BLOCK;
    }

}
