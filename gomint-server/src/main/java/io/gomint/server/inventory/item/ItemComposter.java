package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = -213, sId = "minecraft:composter")
public class ItemComposter extends ItemStack implements io.gomint.inventory.item.ItemComposter {

    @Override
    public ItemType getItemType() {
        return ItemType.COMPOSTER;
    }
}
