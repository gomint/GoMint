/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator;

import io.gomint.GoMint;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockBedrock;
import io.gomint.world.block.BlockDirt;
import io.gomint.world.block.BlockStationaryWater;
import io.gomint.world.block.BlockStone;
import io.gomint.world.block.data.DirtType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 0
 */
public class DefinedBlocks {

    // Used blocks for terrain
    public static final Block WATER = GoMint.instance().createBlock(BlockStationaryWater.class);
    public static final Block BEDROCK = GoMint.instance().createBlock(BlockBedrock.class);
    public static final BlockStone STONE = GoMint.instance().createBlock(BlockStone.class);
    public static final BlockDirt DIRT = GoMint.instance().createBlock(BlockDirt.class);

    static {
        DIRT.type(DirtType.NORMAL);
        STONE.type(BlockStone.Type.STONE);
    }

}
