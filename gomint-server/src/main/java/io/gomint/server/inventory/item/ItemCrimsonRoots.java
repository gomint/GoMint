package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 478 ,sId = "minecraft:crimson_roots" )
public class ItemCrimsonRoots extends ItemStack implements io.gomint.inventory.item.ItemCrimsonRoots {

    @Override
    public String getBlockId() {
        return "minecraft:crimson_roots";
    }

    @Override
    public ItemType getType() {
        return ItemType.CRIMSON_ROOTS;
    }
}
