package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 752 )
public class ItemNetheriteScrap extends ItemStack implements io.gomint.inventory.item.ItemNetheriteScrap {

    @Override
    public ItemType getItemType() {
        return ItemType.NETHERITE_SCRAP;
    }
}
