/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockStoneDoubleSlab;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:real_double_stone_slab")
@RegisterInfo(sId = "minecraft:real_double_stone_slab2")
@RegisterInfo(sId = "minecraft:real_double_stone_slab3")
@RegisterInfo(sId = "minecraft:real_double_stone_slab4")
@RegisterInfo(sId = "minecraft:blackstone_double_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_double_slab")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_double_slab")
public class ItemStoneDoubleSlab extends ItemStack<io.gomint.inventory.item.ItemStoneDoubleSlab> implements io.gomint.inventory.item.ItemStoneDoubleSlab {

    private static final String STONE_SLAB_ID = "minecraft:real_double_stone_slab";
    private static final String STONE_SLAB2_ID = "minecraft:real_double_stone_slab2";
    private static final String STONE_SLAB3_ID = "minecraft:real_double_stone_slab3";
    private static final String STONE_SLAB4_ID = "minecraft:real_double_stone_slab4";

    public enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE(STONE_SLAB_ID, (short) 0),
        WOODEN("minecraft:double_wooden_slab", (short) 0), // This is intended so that creative given "wooden" stone slabs don't break the server
        QUARTZ(STONE_SLAB_ID, (short) 6),
        NETHER_BRICK(STONE_SLAB_ID, (short) 7),

        // Slab types 2
        PURPUR(STONE_SLAB2_ID, (short) 1),
        RED_NETHER_BRICK(STONE_SLAB2_ID, (short) 7),

        // Slab types 3
        END_STONE_BRICK(STONE_SLAB3_ID, (short) 0),
        POLISHED_ANDESITE(STONE_SLAB3_ID, (short) 2),
        ANDESITE(STONE_SLAB3_ID, (short) 3),
        DIORITE(STONE_SLAB3_ID, (short) 4),
        POLISHED_DIORITE(STONE_SLAB3_ID, (short) 5),
        GRANITE(STONE_SLAB3_ID, (short) 6),
        POLISHED_GRANITE(STONE_SLAB3_ID, (short) 7),

        // Slab types 4
        SMOOTH_QUARTZ(STONE_SLAB4_ID, (short) 1),
        STONE(STONE_SLAB4_ID, (short) 2),

        // Additional slabs (new ones)
        BLACKSTONE("minecraft:blackstone_double_slab", (short) 0),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_double_slab", (short) 0),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_double_slab", (short) 0);

        private final String id;
        private final short data;

        StoneTypeMagic(String id, short data) {
            this.id = id;
            this.data = data;
        }
    }

    @Override
    public ItemType itemType() {
        return ItemType.DOUBLE_STONE_SLAB;
    }

    @Override
    public Block block() {
        BlockStoneDoubleSlab slab = this.blocks.get(BlockStoneDoubleSlab.class);
        slab.type(this.type());
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
    public ItemStoneDoubleSlab type(StoneType type) {
        StoneTypeMagic state = StoneTypeMagic.valueOf(type.name());
        this.material(state.id);
        this.data(state.data);
        return this;
    }

}