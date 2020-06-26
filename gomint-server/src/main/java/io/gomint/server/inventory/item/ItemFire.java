package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fire", id = 51 )
 public class ItemFire extends ItemStack implements io.gomint.inventory.item.ItemFire {

    @Override
    public String getBlockId() {
        return "minecraft:fire";
    }

    @Override
    public ItemType getType() {
        return ItemType.FIRE;
    }

}
