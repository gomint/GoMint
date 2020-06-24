package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 535 ,sId = "minecraft:polished_blackstone_bricks" )
public class ItemPolishedBlackStoneBricks extends ItemStack implements io.gomint.inventory.item.ItemPolishedBlackStoneBricks {

    @Override
    public String getBlockId() {
        return "minecraft:polished_blackstone_bricks";
    }

    @Override
    public ItemType getType() {
        return ItemType.POLISHED_BLACKSTONE_BRICKS;
    }
}
