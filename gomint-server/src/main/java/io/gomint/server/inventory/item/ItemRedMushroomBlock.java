package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_mushroom_block", id = 100)
public class ItemRedMushroomBlock extends ItemStack< io.gomint.inventory.item.ItemRedMushroomBlock> implements io.gomint.inventory.item.ItemRedMushroomBlock {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.RED_MUSHROOM_BLOCK;
    }

}
