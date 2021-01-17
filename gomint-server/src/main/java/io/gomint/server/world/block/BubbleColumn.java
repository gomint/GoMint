/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
@RegisterInfo( sId = "minecraft:bubble_column" )
public class BubbleColumn extends Block {

    @Override
    public String blockId() {
        return "minecraft:bubble_column";
    }

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BUBBLE_COLUMN;
    }

}
