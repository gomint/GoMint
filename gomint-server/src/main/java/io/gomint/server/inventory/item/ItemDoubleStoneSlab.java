package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:real_double_stone_slab", id = 43)
public class ItemDoubleStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemDoubleStoneSlab {

    @Override
    public ItemType getType() {
        return ItemType.DOUBLE_STONE_SLAB;
    }

}
