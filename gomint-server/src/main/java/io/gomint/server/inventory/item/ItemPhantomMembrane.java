package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 470 )
public class ItemPhantomMembrane extends ItemStack implements io.gomint.inventory.item.ItemPhantomMembrane {

    @Override
    public ItemType getItemType() {
        return ItemType.PHANTOM_MEMBRANE;
    }

}
