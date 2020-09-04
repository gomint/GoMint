package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 44, sId = "minecraft:double_stone_slab", def = true)
@RegisterInfo(id = 182, sId = "minecraft:double_stone_slab2")
@RegisterInfo(id = -162, sId = "minecraft:double_stone_slab3")
@RegisterInfo(id = -166, sId = "minecraft:double_stone_slab4")
@RegisterInfo(id = -293, sId = "minecraft:polished_blackstone_slab")
@RegisterInfo(id = -284, sId = "minecraft:polished_blackstone_brick_slab")
public class ItemStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemStoneSlab {

    @Override
    public ItemType getItemType() {
        return ItemType.STONE_SLAB;
    }

    @Override
    public String getBlockId() {
        return super.getBlockId().replace("double_", "");
    }

}
