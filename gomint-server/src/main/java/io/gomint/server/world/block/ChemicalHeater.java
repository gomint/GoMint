/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockChemicalHeater;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:chemical_heat")
public class ChemicalHeater extends Block implements BlockChemicalHeater {

    @Override
    public long getBreakTime() {
        return 12500;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHEMICAL_HEATER;
    }

}
