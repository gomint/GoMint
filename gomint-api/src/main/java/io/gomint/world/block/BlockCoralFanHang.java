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
public interface BlockCoralFanHang extends Block {

    /**
     * Get the direction of this coral fan
     *
     * @return direction of the coral
     */
    Direction getDirection();

    /**
     * Set the direction of this coral fan
     *
     * @param direction in which this coral fan should face
     */
    void setDirection(Direction direction);

    /**
     * Set coral type
     *
     * @param type of coral
     */
    void setCoralType(CoralType type);

    /**
     * Get type of coral
     *
     * @return type of coral
     */
    CoralType getCoralType();

    /**
     * Is this coral fan dead?
     *
     * @return true when dead, false otherwise
     */
    boolean isDead();

    /**
     * Set if this coral fan is dead or not
     *
     * @param dead true when it should be dead, false otherwise
     */
    void setDead(boolean dead);

}
