package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 484 ,sId = "minecraft:warped_fungus" )
public class ItemWarpedFungus extends ItemStack implements io.gomint.inventory.item.ItemWarpedFungus  {

    @Override
    public String getBlockId() {
        return "minecraft:warped_fungus";
    }

    @Override
    public ItemType getType() {
        return ItemType.WARPED_FUNGUS;
    }
}
