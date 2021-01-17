package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ladder")
public class ItemLadder extends ItemStack< io.gomint.inventory.item.ItemLadder> implements io.gomint.inventory.item.ItemLadder {

    @Override
    public ItemType itemType() {
        return ItemType.LADDER;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
