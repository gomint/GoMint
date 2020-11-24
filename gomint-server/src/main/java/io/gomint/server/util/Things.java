/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Things {

    public static Facing convertFromDataToBlockFace(short data ) {
        switch ( data ) {
            case 0:
                return Facing.DOWN;
            case 1:
                return Facing.UP;
            case 2:
                return Facing.NORTH;
            case 3:
                return Facing.SOUTH;
            case 4:
                return Facing.WEST;
            case 5:
                return Facing.EAST;
            default:
                return null;
        }
    }

}
