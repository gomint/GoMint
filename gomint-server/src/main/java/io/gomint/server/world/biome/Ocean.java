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
import io.gomint.world.block.BlockGravel;
import io.gomint.world.generator.DefinedBlocks;

@RegisterInfo(sId = "ocean", id = 0)
public class Ocean extends AbstractBiome {

    public Ocean() {
        super(
            new ClimateComponent(0.5f,0.5f),
            new GroundComponent(46, 58,
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ))
        );

        tags("monster", "ocean", "overworld");
    }

}
