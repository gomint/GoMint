/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.GlassColorBlockState;
import io.gomint.world.block.BlockHardenedGlassPane;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.GlassColor;

@RegisterInfo(sId = "minecraft:hard_glass", def = true)
@RegisterInfo(sId = "minecraft:hard_stained_glass")
public class HardenedGlass extends Block implements BlockHardenedGlassPane {

    private static final GlassColorBlockState COLOR = new GlassColorBlockState(() -> new String[]{"color"});

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARDENED_GLASS_PANE;
    }

    @Override
    public GlassColor getColor() {
        if ("minecraft:hard_glass".equals(this.getBlockId())) {
            return GlassColor.TRANSPARENT;
        }

        return COLOR.getState(this);
    }

    @Override
    public void setColor(GlassColor color) {
        if (color == GlassColor.TRANSPARENT) {
            this.setBlockId("minecraft:hard_glass");
            return;
        }

        COLOR.setState(this, color);
    }

}
