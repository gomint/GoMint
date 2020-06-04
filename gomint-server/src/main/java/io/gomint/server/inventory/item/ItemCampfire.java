package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 464 )
public class ItemCampfire extends ItemStack implements io.gomint.inventory.item.ItemCampfire {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public String getBlockId() {
        return "minecraft:campfire";
    }

    @Override
    public ItemType getType() {
        return ItemType.CAMPFIRE;
    }
}
