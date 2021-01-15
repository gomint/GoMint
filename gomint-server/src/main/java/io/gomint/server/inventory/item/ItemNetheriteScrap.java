package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:netherite_scrap", id = 752 )
public class ItemNetheriteScrap extends ItemStack< io.gomint.inventory.item.ItemNetheriteScrap> implements io.gomint.inventory.item.ItemNetheriteScrap {

    @Override
    public ItemType itemType() {
        return ItemType.NETHERITE_SCRAP;
    }
}
