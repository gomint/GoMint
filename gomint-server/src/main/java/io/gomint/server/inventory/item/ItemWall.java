package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockWall;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall", id = 139, def = true)
@RegisterInfo( sId = "minecraft:polished_blackstone_brick_wall", id = -278 )
@RegisterInfo( sId = "minecraft:polished_blackstone_wall", id = -297 )
@RegisterInfo( sId = "minecraft:blackstone_wall", id = -277 )
public class ItemWall extends ItemStack implements io.gomint.inventory.item.ItemWall {

    private static final String WALL_ID = "minecraft:cobblestone_wall";

    private enum StoneTypeMagic {
        SANDSTONE(WALL_ID, (short) 5),
        COBBLESTONE(WALL_ID, (short) 0),
        BRICK(WALL_ID, (short) 6),
        STONE_BRICK(WALL_ID, (short) 7),
        NETHER_BRICK(WALL_ID, (short) 10),
        QUARTZ(WALL_ID, (short) 0),
        SMOOTH_STONE(WALL_ID, (short) 0),

        PRISMARINE_ROUGH(WALL_ID, (short) 11),
        PRISMARINE_BRICK(WALL_ID, (short) 11),
        PRISMARINE_DARK(WALL_ID, (short) 11),
        MOSSY_COBBLESTONE(WALL_ID, (short) 1),
        RED_SANDSTONE(WALL_ID, (short) 12),
        SMOOTH_SANDSTONE(WALL_ID, (short) 5),
        PURPUR(WALL_ID, (short) 0),
        RED_NETHER_BRICK(WALL_ID, (short) 13),

        GRANITE(WALL_ID, (short) 2),
        DIORITE(WALL_ID, (short) 3),
        ANDESITE(WALL_ID, (short) 4),
        POLISHED_GRANITE(WALL_ID, (short) 2),
        POLISHED_DIORITE(WALL_ID, (short) 3),
        POLISHED_ANDESITE(WALL_ID, (short) 4),
        END_STONE_BRICK(WALL_ID, (short) 9),
        SMOOTH_RED_SANDSTONE(WALL_ID, (short) 12),

        STONE(WALL_ID, (short) 0),
        MOSSY_STONE_BRICK(WALL_ID, (short) 1),
        SMOOTH_QUARTZ(WALL_ID, (short) 0),
        CUT_RED_STONE(WALL_ID, (short) 12),
        CUT_SANDSTONE(WALL_ID, (short) 5),

        BLACKSTONE("minecraft:blackstone_wall", (short) 0),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_wall", (short) 0),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_wall", (short) 0);

        private final String wallId;
        private final short data;
        StoneTypeMagic(String wallId, short data) {
            this.wallId = wallId;
            this.data = data;
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.WALL;
    }

    @Override
    public StoneType getStoneType() {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.wallId.equals(this.getMaterial()) && value.data == this.getData()) {
                return StoneType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public void setStoneType(StoneType stoneType) {
        StoneTypeMagic state = StoneTypeMagic.valueOf(stoneType.name());
        this.setMaterial(state.wallId);
        this.setData(state.data);
    }

    @Override
    public Block getBlock() {
        BlockWall block = (BlockWall) super.getBlock();
        block.type(this.getStoneType());
        return block;
    }

}
