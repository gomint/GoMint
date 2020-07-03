package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 489 ,sId = "minecraft:basalt" )
public class ItemBasalt extends ItemStack implements io.gomint.inventory.item.ItemBasalt {

    @Override
    public String getBlockId() {
        return "minecraft:basalt";
    }

    @Override
    public ItemType getType() {
        return ItemType.BASALT;
    }
}
