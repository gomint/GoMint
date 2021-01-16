/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
@RegisterInfo( sId = "minecraft:repeating_command_block" )
public class RepeatingCommandBlock extends Block implements BlockRepeatingCommandBlock {

    @Override
    public String getBlockId() {
        return "minecraft:repeating_command_block";
    }

    @Override
    public float getBlastResistance() {
        return 18000000.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.REPEATING_COMMAND_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
