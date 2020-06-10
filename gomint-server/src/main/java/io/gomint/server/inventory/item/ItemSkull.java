package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:skull", id = 397 )
public class ItemSkull extends ItemStack implements io.gomint.inventory.item.ItemSkull {

    @Override
    public ItemType getType() {
        return ItemType.SKULL;
    }

}
