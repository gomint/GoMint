/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum EffectMagicNumbers {

    SPEED( 1 ),
    SLOWNESS( 2 ),
    HASTE( 3 ),
    MINING_FATIGUE( 4 ),
    STRENGTH( 5 ),
    HEALING( 6 ),
    HARMING( 7 ),
    JUMP( 8 ),
    NAUSEA( 9 ),
    REGENERATION( 10 ),
    DAMAGE_RESISTANCE( 11 ),
    FIRE_RESISTANCE( 12 ),
    WATER_BREATHING( 13 ),
    INVISIBILITY( 14 ),
    BLINDNESS( 15 ),
    NIGHT_VISION( 16 ),
    HUNGER( 17 ),
    WEAKNESS( 18 ),
    POISON( 19 ),
    WITHER( 20 ),
    HEALTH_BOOST( 21 ),
    ABSORPTION( 22 ),
    SATURATION( 23 ),
    LEVITATION( 24 );

    private final int id;

    EffectMagicNumbers( int id ) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
