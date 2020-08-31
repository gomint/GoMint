package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 212 ,sId = "minecraft:border_block" )
public class ItemBorder extends ItemStack implements io.gomint.inventory.item.ItemBorder {

    @Override
    public ItemType getItemType() {
        return ItemType.BORDER;
    }

}
