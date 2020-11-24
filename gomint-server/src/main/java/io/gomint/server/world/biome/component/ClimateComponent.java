/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome.component;

import io.gomint.world.biome.component.Climate;

public class ClimateComponent implements Climate, Component {

    private final float temperature;
    private final float downfall;

    public ClimateComponent(float temperature, float downfall) {
        this.temperature = temperature;
        this.downfall = downfall;
    }

    @Override
    public float temperature() {
        return this.temperature;
    }

    @Override
    public float downfall() {
        return this.downfall;
    }

}
