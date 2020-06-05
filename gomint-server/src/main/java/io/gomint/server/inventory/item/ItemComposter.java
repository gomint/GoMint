package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 000 ) //????
public class ItemComposter extends ItemStack implements io.gomint.inventory.item.ItemComposter {

    @Override
    public String getBlockId() {
        return "minecraft:composter";
    }

    @Override
    public ItemType getType() {
        return ItemType.COMPOSTER;
    }
}
