package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 263 )
public class ItemStrippedAcaciaLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedAcaciaLog {

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_ACACIA_LOG;
    }
}
