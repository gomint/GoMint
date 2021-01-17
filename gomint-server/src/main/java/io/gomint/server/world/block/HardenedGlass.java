/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.GlassColorBlockState;
import io.gomint.world.block.BlockHardenedGlass;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.GlassColor;

@RegisterInfo(sId = "minecraft:hard_glass", def = true)
@RegisterInfo(sId = "minecraft:hard_stained_glass")
public class HardenedGlass extends Block implements BlockHardenedGlass {

    private static final GlassColorBlockState COLOR = new GlassColorBlockState(() -> new String[]{"color"});

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.HARDENED_GLASS_PANE;
    }

    @Override
    public GlassColor color() {
        if ("minecraft:hard_glass".equals(this.blockId())) {
            return GlassColor.TRANSPARENT;
        }

        return COLOR.state(this);
    }

    @Override
    public BlockHardenedGlass color(GlassColor color) {
        if (color == GlassColor.TRANSPARENT) {
            this.blockId("minecraft:hard_glass");
            return this;
        }

        COLOR.state(this, color);
        return this;
    }

}
