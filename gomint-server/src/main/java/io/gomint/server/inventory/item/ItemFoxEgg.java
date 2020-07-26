package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 383 )
public class ItemFoxEgg extends ItemStack implements io.gomint.inventory.item.ItemFoxEgg {

    @Override
    public ItemType getItemType() {
        return ItemType.FOXEGG;
    }
}
