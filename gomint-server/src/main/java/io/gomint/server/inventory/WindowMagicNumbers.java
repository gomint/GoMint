package io.gomint.server.inventory;


/**
 * @author geNAZt
 * @version 1.0
 */
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

    public byte getId() {
        return id;
    }

}
