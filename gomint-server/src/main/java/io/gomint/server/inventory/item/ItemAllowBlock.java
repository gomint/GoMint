package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:allow" )
public class ItemAllowBlock extends ItemStack< io.gomint.inventory.item.ItemAllowBlock> implements io.gomint.inventory.item.ItemAllowBlock {

    @Override
    public ItemType itemType() {
        return ItemType.ALLOW;
    }
}
