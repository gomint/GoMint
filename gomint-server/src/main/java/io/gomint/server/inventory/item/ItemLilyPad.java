package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:waterlily", id = 111)
public class ItemLilyPad extends ItemStack implements io.gomint.inventory.item.ItemLilyPad {

    @Override
    public ItemType getItemType() {
        return ItemType.LILY_PAD;
    }

}
