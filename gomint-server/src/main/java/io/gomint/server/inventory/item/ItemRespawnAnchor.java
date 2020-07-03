package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 527 ,sId = "minecraft:respawn_anchor" )
public class ItemRespawnAnchor extends ItemStack implements io.gomint.inventory.item.ItemRespawnAnchor {

    @Override
    public String getBlockId() {
        return "minecraft:respawn_anchor";
    }

    @Override
    public ItemType getType() {
        return ItemType.RESPAWN_ANCHOR;
    }
}
