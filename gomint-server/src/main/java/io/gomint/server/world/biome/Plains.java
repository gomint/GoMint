/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome;

import io.gomint.GoMint;
import io.gomint.entity.animal.EntityChicken;
import io.gomint.entity.animal.EntityPig;
import io.gomint.entity.animal.EntitySheep;
import io.gomint.entity.monster.EntitySkeleton;
import io.gomint.entity.monster.EntitySpider;
import io.gomint.entity.monster.EntityZombie;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.biome.component.ClimateComponent;
import io.gomint.server.world.biome.component.GroundComponent;
import io.gomint.server.world.biome.component.PopulatorComponent;
import io.gomint.server.world.biome.component.SpawnableEntitiesComponent;
import io.gomint.world.block.BlockGrassBlock;
import io.gomint.world.generator.DefinedBlocks;
import io.gomint.world.generator.populator.TallGrassPopulator;

import java.util.Collections;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "plains", id = 1)
public class Plains extends AbstractBiome {

    /**
     * Construct the plains biome config
     */
    public Plains() {
        super(
            new ClimateComponent(0.8f,0.4f),
            new GroundComponent(63, 68,
                GoMint.instance().createBlock( BlockGrassBlock.class ),
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT),
            createPopulators(),
            new SpawnableEntitiesComponent(Set.of(
                EntitySkeleton.class,
                EntityZombie.class,
                EntitySpider.class,

                EntityPig.class,
                EntitySheep.class,
                EntityChicken.class
            ))
        );

        tags("animal", "monster", "overworld", "plains");
    }

    private static PopulatorComponent createPopulators() {
        TallGrassPopulator populator = new TallGrassPopulator();
        populator.baseAmount( 12 );
        return new PopulatorComponent(Collections.singletonList(populator));
    }

}
