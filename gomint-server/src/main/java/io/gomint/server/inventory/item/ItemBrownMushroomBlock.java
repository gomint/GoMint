package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:brown_mushroom_block", id = 99)
public class ItemBrownMushroomBlock extends ItemStack implements io.gomint.inventory.item.ItemBrownMushroomBlock {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.BROWN_MUSHROOM_BLOCK;
    }

}
