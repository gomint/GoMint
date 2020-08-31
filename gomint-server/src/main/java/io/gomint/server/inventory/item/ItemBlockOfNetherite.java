package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:netherite_block" )
public class ItemBlockOfNetherite extends ItemStack implements io.gomint.inventory.item.ItemBlockOfNetherite {

    @Override
    public ItemType getItemType() {
        return ItemType.NETHERITE_BLOCK;
    }

}
