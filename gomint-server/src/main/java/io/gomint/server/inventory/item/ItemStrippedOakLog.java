package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 265 )
public class ItemStrippedOakLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedOakLog {

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_OAK_LOG;
    }
}
