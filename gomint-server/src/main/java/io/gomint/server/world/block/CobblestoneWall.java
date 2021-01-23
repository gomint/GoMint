/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemCobblestoneWall;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockCobblestoneWall;
import io.gomint.world.block.BlockStoneWall;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CobblestoneType;
import io.gomint.world.block.data.StoneType;

import java.util.Collections;
import java.util.List;

@RegisterInfo(sId = "minecraft:cobblestone_wall[wall_block_type=cobblestone,mossy_cobblestone]", def = true)
public class CobblestoneWall extends Wall<BlockCobblestoneWall> implements BlockCobblestoneWall {

    private static final String STONE_SLAB_ID = "minecraft:cobblestone_wall";
    private static final String STONE_TYPE = "wall_block_type";

    private enum CobblestoneTypeMagic {

        NORMAL(STONE_SLAB_ID, "cobblestone"),
        MOSSY(STONE_SLAB_ID, "mossy_cobblestone");

        private final String value;
        private final String blockId;

        CobblestoneTypeMagic(String blockId, String value) {
            this.value = value;
            this.blockId = blockId;
        }

    }

    private static final EnumBlockState<CobblestoneTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{STONE_TYPE};
    }, CobblestoneTypeMagic.values(), v -> v.value, v -> {
        for (CobblestoneTypeMagic value : CobblestoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public BlockType blockType() {
        return BlockType.STONE_WALL;
    }

    @Override
    public CobblestoneType type() {
        return CobblestoneType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public BlockCobblestoneWall type(CobblestoneType stoneType) {
        CobblestoneTypeMagic newState = CobblestoneTypeMagic.valueOf(stoneType.name());
        if (newState.blockId.isEmpty()) {
            return this;
        }

        this.blockId(newState.blockId);
        VARIANT.state(this, newState);
        return this;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemCobblestoneWall.create(1).type(this.type()));
    }

}
