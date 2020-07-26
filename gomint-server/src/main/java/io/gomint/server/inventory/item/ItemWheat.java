package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 296 )
public class ItemWheat extends ItemStack implements io.gomint.inventory.item.ItemWheat {

    @Override
    public ItemType getItemType() {
        return ItemType.WHEAT;
    }

}
