package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 541 ,sId = "minecraft:chain" )
public class ItemChain extends ItemStack implements io.gomint.inventory.item.ItemChain {

    @Override
    public String getBlockId() {
        return "minecraft:chain";
    }

    @Override
    public ItemType getType() {
        return ItemType.CHAIN;
    }
}
