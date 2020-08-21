/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome;

import io.gomint.GoMint;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.biome.component.ClimateComponent;
import io.gomint.server.world.biome.component.GroundComponent;
import io.gomint.world.block.BlockGrassBlock;
import io.gomint.world.generator.DefinedBlocks;

@RegisterInfo(sId = "plains", id = 1)
public class Plains extends AbstractBiome {

    public Plains() {
        super(
            new ClimateComponent(0.8f,0.4f),
            new GroundComponent(63, 68,
                GoMint.instance().createBlock( BlockGrassBlock.class ),
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT)
        );

        tags("animal", "monster", "overworld", "plains");
    }

}
