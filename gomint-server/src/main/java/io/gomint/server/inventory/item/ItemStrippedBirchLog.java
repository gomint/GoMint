package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 261 )
public class ItemStrippedBirchLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedBirchLog {

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_BIRCH_LOG;
    }
}
