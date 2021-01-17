package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:loom")
public class ItemLoom extends ItemStack< io.gomint.inventory.item.ItemLoom> implements io.gomint.inventory.item.ItemLoom {

    @Override
    public ItemType itemType() {
        return ItemType.LOOM;
    }

}
