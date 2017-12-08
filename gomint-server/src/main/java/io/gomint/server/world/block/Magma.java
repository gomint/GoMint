/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockMagma;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 213 )
public class Magma extends Block implements BlockMagma {

    @Override
    public int getBlockId() {
        return 213;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public BlockType getType() {
        return BlockType.MAGMA;
    }

}
