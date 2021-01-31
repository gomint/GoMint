/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockDriedKelp;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:dried_kelp_block")
public class DriedKelp extends Block implements BlockDriedKelp {

    @Override
    public long breakTime() {
        return 750;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.HOE;
    }

    @Override
    public BlockType blockType() {
        return BlockType.DRIED_KELP;
    }

    @Override
    public float blastResistance() {
        return 12.5f;
    }

}
