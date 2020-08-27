/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

@RegisterInfo( sId = "minecraft:coral_fan_hang" ) // tube, brain
@RegisterInfo( sId = "minecraft:coral_fan_hang2" ) // bubble, fire
@RegisterInfo( sId = "minecraft:coral_fan_hang3" ) // horn
public class CoralFanHang extends Block {

    @Override
    public float getBlastResistance() {
        return 0.1f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_FAN_HANG;
    }

}
