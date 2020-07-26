package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 383 )
public class ItemBeeEgg extends ItemStack implements io.gomint.inventory.item.ItemBeeEgg {

    @Override
    public ItemType getItemType() {
        return ItemType.BEEEGG;
    }

}
