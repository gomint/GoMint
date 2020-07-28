package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:real_double_stone_slab", id = 43)
@RegisterInfo(id = 181, sId = "minecraft:real_double_stone_slab2")
@RegisterInfo(id = -167, sId = "minecraft:real_double_stone_slab3")
@RegisterInfo(id = -168, sId = "minecraft:real_double_stone_slab4")
@RegisterInfo(id = -294, sId = "minecraft:polished_blackstone_double_slab")
@RegisterInfo(id = -285, sId = "minecraft:polished_blackstone_brick_double_slab")
public class ItemDoubleStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemDoubleStoneSlab {

    @Override
    public ItemType getItemType() {
        return ItemType.DOUBLE_STONE_SLAB;
    }

}
