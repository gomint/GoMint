/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.populator;

import io.gomint.util.random.FastRandom;
import io.gomint.world.Chunk;
import io.gomint.world.World;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Populator<E> {

    /**
     * Populate additional structures for a chunk
     *
     * @param world  world
     * @param chunk  which should be populated
     * @param random random instance with which the chunk has been generated
     */
    E populate( World world, Chunk chunk, FastRandom random );

}
