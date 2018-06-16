/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 207 )
public class FrostedIce extends Block implements io.gomint.world.block.BlockFrostedIce {

    @Override
    public int getBlockId() {
        return 207;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.FROSTED_ICE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
