package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 485 ,sId = "minecraft:shroomlight" )
public class ItemShroomLight extends ItemStack implements io.gomint.inventory.item.ItemShroomLight {

    @Override
    public String getBlockId() {
        return "minecraft:shroomlight";
    }

    @Override
    public ItemType getType() {
        return ItemType.SHROOMLIGHT;
    }
}
