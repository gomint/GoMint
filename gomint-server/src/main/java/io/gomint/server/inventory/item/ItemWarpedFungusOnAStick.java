package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 757 )
public class ItemWarpedFungusOnAStick extends ItemStack implements io.gomint.inventory.item.ItemWarpedFungusOnAStick {

    @Override
    public ItemType getType() {
        return ItemType.WARPED_FUNGUS_ON_A_STICK;
    }
}
