package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 211, sId = "minecraft:deny")
public class ItemDenyBlock extends ItemStack implements io.gomint.inventory.item.ItemDenyBlock {

    @Override
    public ItemType getItemType() {
        return ItemType.DENY;
    }

}
