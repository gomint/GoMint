/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:chemistry_table")
public class ChemistryTable extends Block {

    private enum ChemistryTableTypeMagic {
        COMPOUND_CREATOR,
        ELEMENT_CONSTRUCTOR,
        LAB_TABLE,
        MATERIAL_REDUCER,
    }

    private static final EnumBlockState<ChemistryTableTypeMagic, String> TYPE = new EnumBlockState<>(t -> new String[]{"chemistry_table_type"},
        ChemistryTableTypeMagic.values(), v -> v.name().toLowerCase(), s -> ChemistryTableTypeMagic.valueOf(s.toUpperCase()));
    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"});

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHEMISTRY_TABLE;
    }

    @Override
    public long getBreakTime() {
        return 12500;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
