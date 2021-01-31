/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum ParticleMagicNumbers {

    BUBBLE( 1 ),
    CRITICAL( 3 ),
    BLOCK_FORCE_FIELD( 4 ),
    SMOKE( 5 ),
    EXPLODE( 6 ),
    EVAPORATION( 7 ),
    FLAME( 8 ),
    LAVA( 9 ),
    LARGE_SMOKE( 10 ),
    REDSTONE( 11 ),
    RISING_RED_DUST( 12 ),
    ITEM_BREAK( 13 ),
    SNOWBALL_POOF( 14 ),
    HUGE_EXPLODE( 15 ),
    HUGE_EXPLODE_SEED( 16 ),
    MOB_FLAME( 17 ),
    HEART( 18 ),
    TERRAIN( 19 ),
    SUSPENDED_TOWN( 20 ),
    PORTAL( 21 ),
    SPLASH( 23 ),
    WATER_WAKE( 25 ),
    DRIP_WATER( 26 ),
    DRIP_LAVA( 27 ),
    DRIP_HONEY(28),
    FALLING_DUST( 29 ),
    DUST( 29 ),
    MOB_SPELL( 30 ),
    MOB_SPELL_AMBIENT( 31 ),
    MOB_SPELL_INSTANTANEOUS( 32 ),
    NOTE_AND_DUST( 33 ),
    SLIME( 34 ),
    RAIN_SPLASH( 35 ),
    VILLAGER_ANGRY( 36 ),
    VILLAGER_HAPPY( 37 ),
    ENCHANTMENT_TABLE( 38 ),
    TRACKING_EMITTER( 39 ),
    NOTE( 40 ),
    WITCH_SPELL( 41 ),
    CARROT( 42 ),
    END_ROD( 44 ),
    RISING_DRAGONS_BREATH(45),
    SPIT( 46 ),
    TOTEM( 47 ),
    FOOD( 48 ),
    FIREWORKS_STARTER( 49 ),
    FIREWORKS_SPARK( 50 ),
    FIREWORKS_OVERLAY( 51 ),
    BALLOON_GAS( 52) ,
    COLORED_FLAME( 53 ),
    SPARKLER( 54 ),
    CONDUIT( 55 ),
    BUBBLE_COLUMN_UP( 56 ),
    BUBBLE_COLUMN_DOWN( 57 ),
    SNEEZE( 58 ),
    LARGE_EXPLOSION( 61 ),
    INK( 62 ),
    FALLING_RED_DUST( 63 ),
    CAMPFIRE_SMOKE( 64) ,
    FALLING_DRAGONS_BREATH( 66) ,
    DRAGONS_BREATH( 67 ),
    BREAK_BLOCK( -1 ),
    PUNCH_BLOCK( -2 );

    private final int id;

    ParticleMagicNumbers( int id ) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

}
