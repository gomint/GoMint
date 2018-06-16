package io.gomint.server.world.leveldb;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum Generators {

    /**
     * Layered generator, named "Flat" in MC:PE
     */
    FLAT( 2 );

    @Getter
    private final int id;

    /**
     * Construct a new Generators enum value
     *
     * @param id of the generator
     */
    Generators( int id ) {
        this.id = id;
    }

    /**
     * Get a generators ID via its numeric representation
     *
     * @param id which we want to lookup
     * @return generators enum value or null when not found
     */
    public static Generators valueOf( int id ) {
        for ( Generators generators : values() ) {
            if ( generators.id == id ) {
                return generators;
            }
        }

        return null;
    }

}
