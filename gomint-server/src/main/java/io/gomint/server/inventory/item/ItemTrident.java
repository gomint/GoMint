package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 455 )
public class ItemTrident extends ItemStack implements io.gomint.inventory.item.ItemTrident {

    @Override
    public ItemType getType() {
        return ItemType.TRIDENT;
    }

}
