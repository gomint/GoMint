package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:allow" )
public class ItemAllowBlock extends ItemStack implements io.gomint.inventory.item.ItemAllowBlock {

    @Override
    public ItemType getItemType() {
        return ItemType.ALLOW;
    }
}
