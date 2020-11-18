/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.biome.component;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Populator {

    /**
     * Get the list of populators
     *
     * @return lis of populators
     */
    List<io.gomint.world.generator.populator.Populator> getPopulators();

}
