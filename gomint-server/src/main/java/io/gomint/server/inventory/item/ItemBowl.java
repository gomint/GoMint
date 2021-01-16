package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bowl", id = 281 )
 public class ItemBowl extends ItemStack< io.gomint.inventory.item.ItemBowl> implements io.gomint.inventory.item.ItemBowl {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(10000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.BOWL;
    }

}
