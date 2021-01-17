/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBamboo;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:bamboo" )
public class Bamboo extends Block implements BlockBamboo {

    @Override
    public String blockId() {
        return "minecraft:bamboo";
    }

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BAMBOO;
    }

}
