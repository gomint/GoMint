/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum ParticleMagicNumbers {

    BUBBLE( 1 ),
    CRITICAL( 2 ),
    BLOCK_FORCE_FIELD( 3 ),
    SMOKE( 4 ),
    EXPLODE( 5 ),
    EVAPORATION( 6 ),
    FLAME( 7 ),
    LAVA( 8 ),
    LARGE_SMOKE( 9 ),
    REDSTONE( 10 ),
    RISING_RED_DUST( 11 ),
    ITEM_BREAK( 12 ),
    SNOWBALL_POOF( 13 ),
    HUGE_EXPLODE( 14 ),
    HUGE_EXPLODE_SEED( 15 ),
    MOB_FLAME( 16 ),
    HEART( 17 ),
    TERRAIN( 18 ),
    SUSPENDED_TOWN( 19 ),
    PORTAL( 20 ),
    SPLASH( 21 ),
    WATER_WAKE( 22 ),
    DRIP_WATER( 23 ),
    DRIP_LAVA( 24 ),
    FALLING_DUST( 25 ),
    MOB_SPELL( 26 ),
    MOB_SPELL_AMBIENT( 27 ),
    MOB_SPELL_INSTANTANEOUS( 28 ),
    INK( 29 ),
    SLIME( 30 ),
    RAIN_SPLASH( 31 ),
    VILLAGER_ANGRY( 32 ),
    VILLAGER_HAPPY( 33 ),
    ENCHANTMENT_TABLE( 34 ),
    TRACKING_EMITTER( 35 ),
    NOTE( 36 ),
    WITCH_SPELL( 37 ),
    CARROT( 38 ),
    //39 unknown
    END_ROD( 40 ),
    DRAGONS_BREATH( 41 ),
    BREAK_BLOCK( -1 ),
    PUNCH_BLOCK( -2 );

    @Getter
    private final int id;

    ParticleMagicNumbers( int id ) {
        this.id = id;
    }

}
