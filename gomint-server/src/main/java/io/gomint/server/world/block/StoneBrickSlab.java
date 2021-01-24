/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemStoneBrickSlab;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStoneBrickSlab;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.StoneBrickSlabType;

import java.util.Collections;
import java.util.List;

@RegisterInfo(sId = "minecraft:stone_slab[stone_slab_type=stone_brick]", def = true)
@RegisterInfo(sId = "minecraft:stone_slab4[stone_slab_type_4=mossy_stone_brick]")
public class StoneBrickSlab extends Slab<BlockStoneBrickSlab> implements BlockStoneBrickSlab {

    public enum StoneBrickSlabTypeMagic {

        NORMAL("minecraft:stone_slab", "stone_brick"),
        MOSSY("minecraft:stone_slab4", "mossy_stone_brick");

        private final String blockId;
        private final String value;
        StoneBrickSlabTypeMagic(String blockId, String value) {
            this.blockId = blockId;
            this.value = value;
        }

    }

    private static final EnumBlockState<StoneBrickSlabTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{"stone_slab_type", "stone_slab_type_4"};
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
        return BlockType.STONE_BRICK_SLAB;
    }

    @Override
    public StoneBrickSlabType type() {
        return StoneBrickSlabType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public BlockStoneBrickSlab type(StoneBrickSlabType type) {
        StoneBrickSlabTypeMagic newState = StoneBrickSlabTypeMagic.valueOf(type.name());
        this.blockId(newState.blockId);
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
        return Collections.singletonList(ItemStoneBrickSlab.create(1).type(this.type()));
    }

}
