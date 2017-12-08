/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNetherWartBlock;
import io.gomint.world.block.BlockRedNetherBrick;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 215 )
public class RedNetherBrick extends Block implements BlockRedNetherBrick {

    @Override
    public int getBlockId() {
        return 215;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_NETHER_BRICK;
    }

}
