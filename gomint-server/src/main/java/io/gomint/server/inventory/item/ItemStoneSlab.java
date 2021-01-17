package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockStoneSlab;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:double_stone_slab", def = true)
@RegisterInfo(sId = "minecraft:double_stone_slab2")
@RegisterInfo(sId = "minecraft:double_stone_slab3")
@RegisterInfo(sId = "minecraft:double_stone_slab4")
@RegisterInfo(sId = "minecraft:blackstone_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_slab")
public class ItemStoneSlab extends ItemSlab<io.gomint.inventory.item.ItemStoneSlab> implements io.gomint.inventory.item.ItemStoneSlab {

    private static final String STONE_SLAB_ID = "minecraft:double_stone_slab";
    private static final String STONE_SLAB2_ID = "minecraft:double_stone_slab2";
    private static final String STONE_SLAB3_ID = "minecraft:double_stone_slab3";
    private static final String STONE_SLAB4_ID = "minecraft:double_stone_slab4";

    public enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE(STONE_SLAB_ID, (short) 0),
        SANDSTONE(STONE_SLAB_ID, (short) 1),
        WOODEN("minecraft:wooden_slab", (short) 0), // This is intended so that creative given "wooden" stone slabs don't break the server
        COBBLESTONE(STONE_SLAB_ID, (short) 3),
        BRICK(STONE_SLAB_ID, (short) 4),
        STONE_BRICK(STONE_SLAB_ID, (short) 5),
        QUARTZ(STONE_SLAB_ID, (short) 6),
        NETHER_BRICK(STONE_SLAB_ID, (short) 7),

        // Slab types 2
        RED_SANDSTONE(STONE_SLAB2_ID, (short) 0),
        PURPUR(STONE_SLAB2_ID, (short) 1),
        PRISMARINE_ROUGH(STONE_SLAB2_ID, (short) 2),
        PRISMARINE_DARK(STONE_SLAB2_ID, (short) 3),
        PRISMARINE_BRICK(STONE_SLAB2_ID, (short) 4),
        MOSSY_COBBLESTONE(STONE_SLAB2_ID, (short) 5),
        SMOOTH_SANDSTONE(STONE_SLAB2_ID, (short) 6),
        RED_NETHER_BRICK(STONE_SLAB2_ID, (short) 7),

        // Slab types 3
        END_STONE_BRICK(STONE_SLAB3_ID, (short) 0),
        SMOOTH_RED_SANDSTONE(STONE_SLAB3_ID, (short) 1),
        POLISHED_ANDESITE(STONE_SLAB3_ID, (short) 2),
        ANDESITE(STONE_SLAB3_ID, (short) 3),
        DIORITE(STONE_SLAB3_ID, (short) 4),
        POLISHED_DIORITE(STONE_SLAB3_ID, (short) 5),
        GRANITE(STONE_SLAB3_ID, (short) 6),
        POLISHED_GRANITE(STONE_SLAB3_ID, (short) 7),

        // Slab types 4
        MOSSY_STONE_BRICK(STONE_SLAB4_ID, (short) 0),
        SMOOTH_QUARTZ(STONE_SLAB4_ID, (short) 1),
        STONE(STONE_SLAB4_ID, (short) 2),
        CUT_SANDSTONE(STONE_SLAB4_ID, (short) 3),
        CUT_RED_STONE(STONE_SLAB4_ID, (short) 4),

        // Additional slabs (new ones)
        BLACKSTONE("minecraft:blackstone_slab", (short) 0),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_slab", (short) 0),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_slab", (short) 0);

        private final String id;
        private final short data;

        StoneTypeMagic(String id, short data) {
            this.id = id;
            this.data = data;
        }
    }

    @Override
    public ItemType itemType() {
        return ItemType.STONE_SLAB;
    }

    @Override
    public Block block() {
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier(this.material().replace("double_", ""), null);
        BlockStoneSlab slab = (BlockStoneSlab) this.blocks.get(identifier);
        slab.type(this.type());
        slab.top(this.top());
        return slab;
    }

    @Override
    public StoneType type() {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.id.equals(this.material()) && value.data == this.data()) {
                return StoneType.valueOf(value.name());
            }
        }

        return StoneType.SMOOTH_STONE;
    }

    @Override
    public io.gomint.inventory.item.ItemStoneSlab type(StoneType type) {
        StoneTypeMagic state = StoneTypeMagic.valueOf(type.name());
        this.material(state.id);
        this.data(state.data);
        return this;
    }

}
