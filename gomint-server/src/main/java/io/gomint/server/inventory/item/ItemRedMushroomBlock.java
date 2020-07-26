package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_mushroom_block", id = 100)
public class ItemRedMushroomBlock extends ItemStack implements io.gomint.inventory.item.ItemRedMushroomBlock {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.RED_MUSHROOM_BLOCK;
    }

}
