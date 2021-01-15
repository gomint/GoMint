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
public interface BlockCoralFanHang extends Block, BlockDirection<BlockCoralFanHang> {

    /**
     * Set coral type
     *
     * @param type of coral
     * @return block for chaining
     */
    BlockCoralFanHang coralType(CoralType type);

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
    BlockCoralFanHang dead(boolean dead);

}
