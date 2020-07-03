package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 487 ,sId = "minecraft:crimson_nylium")
public class ItemCrimsonNylium extends ItemStack implements io.gomint.inventory.item.ItemCrimsonNylium {

    @Override
    public String getBlockId() {
        return "minecraft:crimson_nylium";
    }

    @Override
    public ItemType getType() {
        return ItemType.CRIMSON_NYLIUM;
    }
}
