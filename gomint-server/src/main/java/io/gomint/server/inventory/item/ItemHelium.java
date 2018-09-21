package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 268 )
public class ItemHelium extends ItemStack {

    @Override
    public ItemType getType() {
        return ItemType.HELIUM;
    }
}
