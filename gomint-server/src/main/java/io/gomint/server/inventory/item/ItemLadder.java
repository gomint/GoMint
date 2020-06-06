package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 65)
public class ItemLadder extends ItemStack implements io.gomint.inventory.item.ItemLadder {

    @Override
    public String getBlockId() {
        return "minecraft:ladder";
    }

    @Override
    public ItemType getType() {
        return ItemType.LADDER;
    }

    @Override
    public long getBurnTime() {
        return 15000;
    }

}
