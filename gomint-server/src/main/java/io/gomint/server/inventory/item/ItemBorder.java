package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 212 ,sId = "minecraft:border_block" )
public class ItemBorder extends ItemStack< io.gomint.inventory.item.ItemBorder> implements io.gomint.inventory.item.ItemBorder {

    @Override
    public ItemType itemType() {
        return ItemType.BORDER;
    }

}
