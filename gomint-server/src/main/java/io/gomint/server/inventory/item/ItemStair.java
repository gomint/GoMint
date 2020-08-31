package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -169, sId = "minecraft:granite_stairs")
@RegisterInfo( id = -170, sId = "minecraft:diorite_stairs")
@RegisterInfo( id = -171, sId = "minecraft:andesite_stairs")
@RegisterInfo( id = -172, sId = "minecraft:polished_granite_stairs")
@RegisterInfo( id = -173, sId = "minecraft:polished_diorite_stairs")
@RegisterInfo( id = -174, sId = "minecraft:polished_andesite_stairs")
@RegisterInfo( id = -175, sId = "minecraft:mossy_stone_brick_stairs")
@RegisterInfo( id = -176, sId = "minecraft:smooth_red_sandstone_stairs")
@RegisterInfo( id = -177, sId = "minecraft:smooth_sandstone_stairs")
@RegisterInfo( id = -178, sId = "minecraft:end_brick_stairs")
@RegisterInfo( id = -179, sId = "minecraft:mossy_cobblestone_stairs")
@RegisterInfo( id = -180, sId = "minecraft:normal_stone_stairs", def = true)
@RegisterInfo( id = -184, sId = "minecraft:red_nether_brick_stairs")
@RegisterInfo( id = -185, sId = "minecraft:smooth_quartz_stairs")
public class ItemStair extends ItemStack implements io.gomint.inventory.item.ItemStair {

    @Override
    public ItemType getItemType() {
        return ItemType.STAIR;
    }

}
