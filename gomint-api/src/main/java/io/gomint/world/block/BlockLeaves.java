/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockLeaves extends Block {

    public enum Type {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }

    /**
     * Set the type of leave
     *
     * @param type of the leave
     */
    void setLeaveType( Type type );

    /**
     * Get type of leave
     *
     * @return type of leave
     */
    Type getLeaveType();

}
