/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemStoneBrickDoubleSlab;
import io.gomint.inventory.item.ItemStoneBrickWall;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStoneBrickDoubleSlab;
import io.gomint.world.block.BlockStoneBrickWall;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.StoneBrickSlabType;

import java.util.Collections;
import java.util.List;

@RegisterInfo(sId = "minecraft:cobblestone_wall[wall_block_type=stone_brick,mossy_stone_brick]")
public class StoneBrickWall extends Block implements BlockStoneBrickWall {

    public enum StoneBrickSlabTypeMagic {

        NORMAL( "stone_brick"),
        MOSSY( "mossy_stone_brick");

        private final String value;
        StoneBrickSlabTypeMagic(String value) {
            this.value = value;
        }

    }

    private static final EnumBlockState<StoneBrickSlabTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{"wall_block_type"};
    }, StoneBrickSlabTypeMagic.values(), v -> v.value, v -> {
        for (StoneBrickSlabTypeMagic value : StoneBrickSlabTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return StoneBrickSlabTypeMagic.NORMAL;
    });

    @Override
    public BlockType blockType() {
        return BlockType.STONE_BRICK_WALL;
    }

    @Override
    public StoneBrickSlabType type() {
        return StoneBrickSlabType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public BlockStoneBrickWall type(StoneBrickSlabType type) {
        StoneBrickSlabTypeMagic newState = StoneBrickSlabTypeMagic.valueOf(type.name());
        VARIANT.state(this, newState);
        return this;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public long breakTime() {
        return this.type() == StoneBrickSlabType.MOSSY ? 7500 : 10000;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemStoneBrickWall.create(1).type(this.type()));
    }

}
