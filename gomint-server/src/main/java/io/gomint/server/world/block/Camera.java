/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCamera;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:camera")
public class Camera extends Block implements BlockCamera {

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CAMERA;
    }

}
