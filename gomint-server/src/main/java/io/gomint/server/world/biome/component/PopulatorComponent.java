/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome.component;

import io.gomint.world.biome.component.Populator;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PopulatorComponent implements Populator, Component {

    private final List<io.gomint.world.generator.populator.Populator> populators;

    /**
     * Construct a new populator holder
     *
     * @param populators which this holder holds
     */
    public PopulatorComponent(List<io.gomint.world.generator.populator.Populator> populators) {
        this.populators = populators;
    }

    @Override
    public List<io.gomint.world.generator.populator.Populator> getPopulators() {
        return this.populators;
    }

}
