/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.RotationDirection;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockCoralFan extends Block {

    /**
     * Get the direction of this coral fan
     *
     * @return direction of the coral
     */
    RotationDirection rotation();

    /**
     * Set the direction of this coral fan
     *
     * @param direction in which this coral fan should face
     * @return block for chaining
     */
    BlockCoralFan rotation(RotationDirection direction);

    /**
     * Set coral type
     *
     * @param type of coral
     * @return block for chaining
     */
    BlockCoralFan coralType(CoralType type);

    /**
     * Get type of coral
     *
     * @return type of coral
     */
    CoralType coralType();

    /**
     * Is this coral fan dead?
     *
     * @return true when dead, false otherwise
     */
    boolean dead();

    /**
     * Set if this coral fan is dead or not
     *
     * @param dead true when it should be dead, false otherwise
     * @return block for chaining
     */
    BlockCoralFan dead(boolean dead);

}
