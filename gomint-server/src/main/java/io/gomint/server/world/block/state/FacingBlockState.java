/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FacingBlockState extends BlockState<Facing> {

    @Override
    public byte toData() {
        switch ( this.getState() ) {
            case SOUTH:
                return 2;
            case NORTH:
                return 3;
            case EAST:
                return 0;
            case WEST:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public void fromData( byte data ) {
        switch ( data ) {
            case 0:
                this.setState( Facing.EAST );
                break;
            case 1:
                this.setState( Facing.WEST );
                break;
            case 2:
                this.setState( Facing.SOUTH );
                break;
            case 3:
                this.setState( Facing.NORTH );
                break;
            default:
                this.setState( Facing.EAST );
        }
    }

}
