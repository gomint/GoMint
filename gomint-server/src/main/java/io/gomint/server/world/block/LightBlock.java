/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockLightBlock;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:light_block")
public class LightBlock extends Block implements BlockLightBlock {

    private static final ProgressBlockState LIGHT_LEVEL = new ProgressBlockState(() -> new String[]{"block_light_level"}, 15, a -> {});

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.LIGHT_BLOCK;
    }

    @Override
    public float intensity() {
        return LIGHT_LEVEL.state(this);
    }

    @Override
    public BlockLightBlock intensity(float intensity) {
        LIGHT_LEVEL.state(this, intensity);
        return this;
    }

}
