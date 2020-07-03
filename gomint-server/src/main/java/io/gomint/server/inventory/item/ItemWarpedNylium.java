package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 488 ,sId = "minecraft:warped_nylium" )
public class ItemWarpedNylium extends ItemStack implements io.gomint.inventory.item.ItemWarpedNylium {

    @Override
    public String getBlockId() {
        return "minecraft:warped_nylium";
    }

    @Override
    public ItemType getType() {
        return ItemType.WARPED_NYLIUM;
    }
}
