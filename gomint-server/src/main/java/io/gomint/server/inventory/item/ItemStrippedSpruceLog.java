package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 260 )
public class ItemStrippedSpruceLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedSpruceLog {

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_SPRUCE_LOG;
    }
}
