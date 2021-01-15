/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockLightBlock extends Block {

    /**
     * Get light intensity, 0 for off, 1 for 100%
     *
     * @return 0 to 1
     */
    float intensity();

    /**
     * Set the intensity of light
     *
     * @param intensity ranging from 0 to 1
     * @return block for chaining
     */
    BlockLightBlock intensity(float intensity);

}
