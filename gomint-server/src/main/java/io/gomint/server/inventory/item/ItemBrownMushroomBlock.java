package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:brown_mushroom_block", id = 99)
public class ItemBrownMushroomBlock extends ItemStack< io.gomint.inventory.item.ItemBrownMushroomBlock> implements io.gomint.inventory.item.ItemBrownMushroomBlock {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.BROWN_MUSHROOM_BLOCK;
    }

}
