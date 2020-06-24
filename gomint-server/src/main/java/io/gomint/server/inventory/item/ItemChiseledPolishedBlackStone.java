package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:chiseled_polished_blackstone" ) //TODO get ID??
public class ItemChiseledPolishedBlackStone extends ItemStack implements io.gomint.inventory.item.ItemChiseledPolishedBlackstone {

    @Override
    public String getBlockId() {
        return "minecraft:chiseled_polished_blackstone";
    }

    @Override
    public ItemType getType() {
        return ItemType.CHISELED_POLISHED_BLACKSTONE;
    }
}
