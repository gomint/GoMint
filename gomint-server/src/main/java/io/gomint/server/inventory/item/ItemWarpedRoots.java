package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:warped_roots" ) //TODO get ID ???
public class ItemWarpedRoots extends ItemStack implements io.gomint.inventory.item.ItemWarpedRoots {

    @Override
    public String getBlockId() {
        return "minecraft:warped_roots";
    }

    @Override
    public ItemType getType() {
        return ItemType.WARPED_ROOTS;
    }
}
