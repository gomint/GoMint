package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:phantom_membrane", id = 470 )
public class ItemPhantomMembrane extends ItemStack< io.gomint.inventory.item.ItemPhantomMembrane> implements io.gomint.inventory.item.ItemPhantomMembrane {

    @Override
    public ItemType itemType() {
        return ItemType.PHANTOM_MEMBRANE;
    }

}
