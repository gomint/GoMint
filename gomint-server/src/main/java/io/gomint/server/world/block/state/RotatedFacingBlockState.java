/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class RotatedFacingBlockState extends BlockState<Facing> {

    /**
     * Detect the facing of this state from the rotation of the player
     *
     * @param player from which we get the rotation
     */
    public void detectFromPlayer( EntityPlayer player ) {
        float rotation = ( player.getYaw() ) % 360;
        if ( rotation < 0 ) {
            rotation += 360.0;
        }

        if ( ( rotation >= 0 && rotation < 45 ) || ( rotation >= 315 && rotation < 360 ) ) {
            this.setState( Facing.SOUTH );
        } else if ( rotation >= 45 && rotation < 135 ) {
            this.setState( Facing.WEST );
        } else if ( rotation >= 135 && rotation < 225 ) {
            this.setState( Facing.NORTH );
        } else if ( rotation >= 225 && rotation < 315 ) {
            this.setState( Facing.EAST );
        }
    }

    @Override
    public short toData() {
        switch ( this.getState() ) {
            case SOUTH:
                return 0;
            case NORTH:
                return 2;
            case EAST:
                return 3;
            case WEST:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public void fromData( short data ) {
        switch ( data ) {
            case 0:
                this.setState( Facing.SOUTH );
                break;
            case 1:
                this.setState( Facing.WEST );
                break;
            case 2:
                this.setState( Facing.NORTH );
                break;
            case 3:
                this.setState( Facing.EAST );
                break;
            default:
                this.setState( Facing.SOUTH );
        }
    }

}
