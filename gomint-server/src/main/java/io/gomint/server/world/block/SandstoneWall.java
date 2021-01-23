/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemSandstoneWall;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockSandstoneWall;
import io.gomint.world.block.BlockStoneWall;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.StoneType;

import java.util.Collections;
import java.util.List;

@RegisterInfo(sId = "minecraft:cobblestone_wall[wall_block_type=sandstone,red_sandstone]")
public class SandstoneWall extends Wall<BlockSandstoneWall> implements BlockSandstoneWall {

    private static final String STONE_TYPE = "wall_block_type";

    private enum SandcolorMagic {

        NORMAL("sandstone"),
        RED("red_sandstone");

        private final String value;

        SandcolorMagic(String value) {
            this.value = value;
        }

    }

    private static final EnumBlockState<SandcolorMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{STONE_TYPE};
    }, SandcolorMagic.values(), v -> v.value, v -> {
        for (SandcolorMagic value : SandcolorMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public BlockType blockType() {
        return BlockType.SANDSTONE_WALL;
    }

    @Override
    public BlockSandstoneWall color(Sandcolor color) {
        VARIANT.state(this, SandcolorMagic.valueOf(color.name()));
        return this;
    }

    @Override
    public Sandcolor color() {
        return Sandcolor.valueOf(VARIANT.state(this).name());
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemSandstoneWall.create(1).color(this.color()));
    }

}
