package io.gomint.server.world;

/**
 * @author geNAZt
 * @version 1.0
 *          <p>
 *          Documentation can be looked up in the API enum
 */
// CHECKSTYLE:OFF
public enum GamemodeMagicNumbers {

    SURVIVAL( 0 ),
    CREATIVE( 1 ),
    ADVENTURE( 2 ),
    SPECTATOR( 3 );

    private final int magicNumber;

    GamemodeMagicNumbers( int magicNumber ) {
        this.magicNumber = magicNumber;
    }

    public int getMagicNumber() {
        return this.magicNumber;
    }
}
// CHECKSTYLE:ON
