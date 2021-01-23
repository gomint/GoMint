package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:granite_stairs")
@RegisterInfo( sId = "minecraft:diorite_stairs")
@RegisterInfo( sId = "minecraft:andesite_stairs")
@RegisterInfo( sId = "minecraft:polished_granite_stairs")
@RegisterInfo( sId = "minecraft:polished_diorite_stairs")
@RegisterInfo( sId = "minecraft:polished_andesite_stairs")
@RegisterInfo( sId = "minecraft:end_brick_stairs")
@RegisterInfo( sId = "minecraft:normal_stone_stairs", def = true)
@RegisterInfo( sId = "minecraft:red_nether_brick_stairs")
@RegisterInfo( sId = "minecraft:smooth_quartz_stairs")
public class ItemStair extends ItemStack< io.gomint.inventory.item.ItemStair> implements io.gomint.inventory.item.ItemStair {

    @Override
    public ItemType itemType() {
        return ItemType.STAIR;
    }

}
