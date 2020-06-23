package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:crimson_fungus" ) //TODO get ID ???
public class ItemCrackedNetherBrick extends ItemStack implements io.gomint.inventory.item.ItemCrackedNetherBrick {

    @Override
    public String getBlockId() {
        return "minecraft:crimson_fungus";
    }

    @Override
    public ItemType getType() {
        return ItemType.CRIMSON_FUNGUS;
    }

}
