package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@RegisterInfo( id = -180, sId = "minecraft:normal_stone_stairs")
@RegisterInfo( id = -184, sId = "minecraft:red_nether_brick_stairs")
@RegisterInfo( id = -185, sId = "minecraft:smooth_quartz_stairs")
public class ItemStair extends ItemStack implements io.gomint.inventory.item.ItemStair {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStair.class);

    @Override
    public String getBlockId() {
        switch (this.getMaterial()) {
            case -169:
                return "minecraft:granite_stairs";
            case -170:
                return "minecraft:diorite_stairs";
            case -171:
                return "minecraft:andesite_stairs";
            case -172:
                return "minecraft:polished_granite_stairs";
            case -173:
                return "minecraft:polished_diorite_stairs";
            case -174:
                return "minecraft:polished_andesite_stairs";
            case -175:
                return "minecraft:mossy_stone_brick_stairs";
            case -176:
                return "minecraft:smooth_red_sandstone_stairs";
            case -177:
                return "minecraft:smooth_sandstone_stairs";
            case -178:
                return "minecraft:end_brick_stairs";
            case -179:
                return "minecraft:mossy_cobblestone_stairs";
            case -180:
                return "minecraft:normal_stone_stairs";
            case -184:
                return "minecraft:red_nether_brick_stairs";
            case -185:
                return "minecraft:smooth_quartz_stairs";
        }

        LOGGER.warn("Unknown stair type: {}", this.getMaterial());
        return "unknown";
    }

    @Override
    public ItemType getType() {
        return ItemType.STAIR;
    }

}
