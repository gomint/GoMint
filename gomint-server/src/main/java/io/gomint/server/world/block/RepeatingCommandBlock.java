/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRepeatingCommandBlock;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 188 )
public class RepeatingCommandBlock extends Block implements BlockRepeatingCommandBlock {

    @Override
    public int getBlockId() {
        return 188;
    }

    @Override
    public float getBlastResistance() {
        return 18000000.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.REPEATING_COMMAND_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
