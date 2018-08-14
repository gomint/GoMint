package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 264 )
public class ItemStrippedDarkOakLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedDarkOakLog {

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_DARK_OAK_LOG;
    }
}
