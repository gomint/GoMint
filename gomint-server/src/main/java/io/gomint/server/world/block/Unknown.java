/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:unknown")
public class Unknown extends Block {
    @Override
    public BlockType blockType() {
        return BlockType.UNKNOWN;
    }

    @Override
    public float blastResistance() {
        return 0;
    }
}
