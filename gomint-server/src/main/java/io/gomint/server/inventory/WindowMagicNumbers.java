package io.gomint.server.inventory;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public enum WindowMagicNumbers {

    // CHECKSTYLE:OFF
    PLAYER( 0 ),
    FIRST( 1 ),
    LAST( 100 ),
    OFFHAND( 119 ),
    ARMOR( 120 ),
    CURSOR( 124 );

    private final byte id;

    WindowMagicNumbers( int id ) {
        this.id = (byte) id;
    }

}
