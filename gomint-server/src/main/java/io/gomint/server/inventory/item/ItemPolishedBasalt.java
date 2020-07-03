package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:polished_basalt" ) //TODO get ID ???
public class ItemPolishedBasalt extends ItemStack implements io.gomint.inventory.item.ItemPolishedBasalt {

    @Override
    public String getBlockId() {
        return "minecraft:polished_basalt";
    }

    @Override
    public ItemType getType() {
        return ItemType.POLISHED_BASALT;
    }
}
