/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemStoneBrick;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStoneBrick;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.StoneBrickType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stonebrick" )
public class StoneBrick extends Block implements BlockStoneBrick {

    public enum StoneBrickTypeMagic {

        NORMAL("default"),
        CRACKED("cracked"),
        MOSSY("mossy"),
        CHISELED("chiseled"),
        SMOOTH("smooth");

        private final String type;
        StoneBrickTypeMagic(String type) {
            this.type = type;
        }
    }

    private static final EnumBlockState<StoneBrickTypeMagic, String> VARIANT = new EnumBlockState<>(v -> new String[]{"stone_brick_type"},
        StoneBrickTypeMagic.values(), v -> v.type, s -> {
        for (StoneBrickTypeMagic value : StoneBrickTypeMagic.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long breakTime() {
        return 2250;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.STONE_BRICK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockStoneBrick type(StoneBrickType type) {
        VARIANT.state(this, StoneBrickTypeMagic.valueOf(type.name()));
        return this;
    }

    @Override
    public StoneBrickType type() {
        return StoneBrickType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemStoneBrick.create(1).type(this.type()));
    }

}
