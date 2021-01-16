/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome;

import io.gomint.GoMint;
import io.gomint.entity.animal.EntitySquid;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.biome.component.ClimateComponent;
import io.gomint.server.world.biome.component.GroundComponent;
import io.gomint.server.world.biome.component.PopulatorComponent;
import io.gomint.server.world.biome.component.SpawnableEntitiesComponent;
import io.gomint.world.block.BlockGravel;
import io.gomint.world.generator.populator.TallGrassPopulator;

import java.util.Collections;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "ocean", id = 0)
public class Ocean extends AbstractBiome {

    /**
     * Construct the ocean biome config
     */
    public Ocean() {
        super(
            new ClimateComponent(0.5f, 0.5f),
            new GroundComponent(46, 58,
                GoMint.instance().createBlock(BlockGravel.class),
                GoMint.instance().createBlock(BlockGravel.class),
                GoMint.instance().createBlock(BlockGravel.class),
                GoMint.instance().createBlock(BlockGravel.class),
                GoMint.instance().createBlock(BlockGravel.class)),
            createPopulators(),
            new SpawnableEntitiesComponent(Set.of(
                EntitySquid.class
            ))
        );

        tags("monster", "ocean", "overworld");
    }

    private static PopulatorComponent createPopulators() {
        TallGrassPopulator populator = new TallGrassPopulator();
        populator.baseAmount( 5 );
        return new PopulatorComponent(Collections.singletonList(populator));
    }

}
