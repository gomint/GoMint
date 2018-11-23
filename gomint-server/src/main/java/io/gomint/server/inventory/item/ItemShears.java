package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 359 )
public class ItemShears extends ItemReduceTierDiamond implements io.gomint.inventory.item.ItemShears {


    @Override
    public ItemType getType() {
        return ItemType.SHEARS;
    }

}
