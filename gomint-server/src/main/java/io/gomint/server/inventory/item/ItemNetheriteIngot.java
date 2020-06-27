package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 742 )
public class ItemNetheriteIngot extends ItemStack implements io.gomint.inventory.item.ItemNetheriteIngot {

    @Override
    public ItemType getType() {
        return ItemType.NETHERITE_INGOT;
    }
}
