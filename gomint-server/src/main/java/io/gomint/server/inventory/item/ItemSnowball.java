package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 332 )
public class ItemSnowball extends ItemStack implements io.gomint.inventory.item.ItemSnowball {



    @Override
    public ItemType getType() {
        return ItemType.SNOWBALL;
    }

}
