/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.server.util.Things;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockfaceBlockState extends BlockState<BlockFace> {

    @Override
    public byte toData() {
        switch ( this.getState() ) {
            case DOWN:
                return 0;
            case UP:
                return 1;
            case NORTH:
                return 2;
            case SOUTH:
                return 3;
            case WEST:
                return 4;
            case EAST:
                return 5;
            default:
                return 0;
        }
    }

    @Override
    public void fromData( byte data ) {
        this.setState( Things.convertFromDataToBlockFace( data ) );
    }

}
