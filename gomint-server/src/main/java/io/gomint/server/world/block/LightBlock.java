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
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIGHT_BLOCK;
    }

    @Override
    public float getIntensity() {
        return LIGHT_LEVEL.getState(this);
    }

    @Override
    public void setIntensity(float intensity) {
        LIGHT_LEVEL.setState(this, intensity);
    }

}
