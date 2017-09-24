package io.gomint.server.network.type;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum WindowType {

    // CHECKSTYLE:OFF
    INVENTORY( -1 ),
    CONTAINER( 0 ),
    WORKBENCH( 1 ),
    FURNACE( 2 ),
    ENCHANTMENT( 3 ),
    BREWING_STAND( 4 ),
    ANVIL( 5 ),
    DISPENSER( 6 ),
    DROPPER( 7 ),
    HOPPER( 8 ),
    CAULDRON( 9 ),
    MINECART_CHEST( 10 ),
    MINECART_HOPPER( 11 ),
    HORSE( 12 ),
    BEACON( 13 ),
    STRUCTURE_EDITOR( 14 ),
    TRADING( 15 ),
    COMMAND_BLOCK( 16 );

    @Getter private final byte id;
    WindowType( int id ) {
        this.id = (byte) id;
    }

}
