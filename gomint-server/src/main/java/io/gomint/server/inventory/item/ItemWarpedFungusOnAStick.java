package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:warped_fungus_on_a_stick", id = 757 )
public class ItemWarpedFungusOnAStick extends ItemStack< io.gomint.inventory.item.ItemWarpedFungusOnAStick> implements io.gomint.inventory.item.ItemWarpedFungusOnAStick {

    @Override
    public ItemType itemType() {
        return ItemType.WARPED_FUNGUS_ON_A_STICK;
    }
}
