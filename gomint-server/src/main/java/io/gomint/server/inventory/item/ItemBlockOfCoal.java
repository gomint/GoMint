package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coal_block", id = 173)
public class ItemBlockOfCoal extends ItemStack< io.gomint.inventory.item.ItemBlockOfCoal> implements io.gomint.inventory.item.ItemBlockOfCoal {

    @Override
    public ItemType itemType() {
        return ItemType.BLOCK_OF_COAL;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(800000);
    }

}
