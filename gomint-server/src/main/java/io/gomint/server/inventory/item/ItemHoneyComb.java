package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 736 )
public class ItemHoneyComb extends ItemStack implements io.gomint.inventory.item.ItemHoneyComb {

    @Override
    public ItemType getType() {
        return ItemType.HONEYCOMB;
    }
}
