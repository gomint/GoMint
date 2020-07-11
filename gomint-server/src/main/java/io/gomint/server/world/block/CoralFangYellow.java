/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCoralFangYellow;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coral_fan_hang3" )
public class CoralFangYellow extends Block implements BlockCoralFangYellow {

    @Override
    public float getBlastResistance() {
        return 0.1f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_FANG_YELLOW;
    }

}
