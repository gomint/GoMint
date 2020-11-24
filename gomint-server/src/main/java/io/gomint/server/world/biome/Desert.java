/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome;

import io.gomint.entity.monster.EntitySkeleton;
import io.gomint.entity.monster.EntitySpider;
import io.gomint.entity.monster.EntityZombie;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.biome.component.ClimateComponent;
import io.gomint.server.world.biome.component.SpawnableEntitiesComponent;

import java.util.Set;

@RegisterInfo(sId = "desert", id = 2)
public class Desert extends AbstractBiome {

    /**
     * Construct the desert biome config
     */
    public Desert() {
        super(
            new ClimateComponent(2.0f, 0.0f),
            new SpawnableEntitiesComponent(Set.of(
                EntitySkeleton.class,
                EntityZombie.class,
                EntitySpider.class
            ))
        );

        tags("monster", "desert", "overworld");
    }

}
