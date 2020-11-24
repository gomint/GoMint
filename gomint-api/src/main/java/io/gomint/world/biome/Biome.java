/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.biome;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface Biome {

    /**
     * Get the component for this biome
     *
     * @param componentClass class of the component which we want to get
     * @param <Component> type of the component
     * @return null if the biome doesn't have this component, the component instance otherwise
     */
    <Component> Component component(Class<Component> componentClass);

    /**
     * Tags of this biome
     *
     * @return set of tags
     */
    Set<String> tags();

}
