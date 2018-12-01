package io.gomint.server.entity;

import lombok.Getter;

/**
 * @author geNAZt
 */
public enum EntityFlag {

    PLAYER_SLEEP( 1 ),

    ONFIRE( 0 ),
    SNEAKING( 1 ),
    RIDING( 2 ),
    SPRINTING( 3 ),
    ACTION( 4 ),
    INVISIBLE( 5 ),
    IGNITED( 10 ),
    BABY( 11 ),
    CAN_SHOW_NAMETAG( 14 ),
    ALWAYS_SHOW_NAMETAG( 15 ),
    IMMOBILE( 16 ),
    CAN_CLIMB( 19 ),
    SWIMMER( 20 ),
    CAN_FLY( 21 ),
    CHARGED( 27 ),
    GLIDING( 32 ),
    BREATHING( 35 ),

    HAS_COLLISION( 47 ),
    AFFECTED_BY_GRAVITY( 48 ),

    DANCING( 50 ),

    SPINNING( 55 ),
    SWIMMING( 56 );

    @Getter
    private final int id;

    EntityFlag( int id ) {
        this.id = id;
    }
}
