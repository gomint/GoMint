package io.gomint.world.block;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum BlockFace {
    DOWN( 0 ),
    UP( 1 ),
    EAST( 2 ),
    WEST( 3 ),
    NORTH( 4 ),
    SOUTH( 5 );

    @Getter
    private final int value;

    BlockFace( int value ) {
        this.value = value;
    }
}
