package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 262 )
public class ItemStrippedJungleLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedJungleLog {

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_JUNGLE_LOG;
    }
}
