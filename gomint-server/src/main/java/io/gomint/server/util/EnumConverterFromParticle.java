/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.world.Particle;

/**
 * @author generated
 * @version 2.0
 */
public class EnumConverterFromParticle implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return Particle.BUBBLE;
            case 2:
                return Particle.CRITICAL;
            case 3:
                return Particle.BLOCK_FORCE_FIELD;
            case 4:
                return Particle.SMOKE;
            case 5:
                return Particle.EXPLODE;
            case 6:
                return Particle.EVAPORATION;
            case 7:
                return Particle.FLAME;
            case 8:
                return Particle.LAVA;
            case 9:
                return Particle.LARGE_SMOKE;
            case 10:
                return Particle.REDSTONE;
            case 11:
                return Particle.RISING_RED_DUST;
            case 12:
                return Particle.ITEM_BREAK;
            case 13:
                return Particle.SNOWBALL_POOF;
            case 14:
                return Particle.HUGE_EXPLODE;
            case 15:
                return Particle.HUGE_EXPLODE_SEED;
            case 16:
                return Particle.MOB_FLAME;
            case 17:
                return Particle.HEART;
            case 18:
                return Particle.TERRAIN;
            case 19:
                return Particle.SUSPENDED_TOWN;
            case 20:
                return Particle.PORTAL;
            case 22:
                return Particle.SPLASH;
            case 24:
                return Particle.WATER_WAKE;
            case 25:
                return Particle.DRIP_WATER;
            case 26:
                return Particle.DRIP_LAVA;
            case 27:
                return Particle.DRIP_HONEY;
            case 28:
                return Particle.FALLING_DUST;
            case 29:
                return Particle.MOB_SPELL;
            case 30:
                return Particle.MOB_SPELL_AMBIENT;
            case 31:
                return Particle.MOB_SPELL_INSTANTANEOUS;
            case 32:
                return Particle.NOTE_AND_DUST;
            case 33:
                return Particle.SLIME;
            case 34:
                return Particle.RAIN_SPLASH;
            case 35:
                return Particle.VILLAGER_ANGRY;
            case 36:
                return Particle.VILLAGER_HAPPY;
            case 37:
                return Particle.ENCHANTMENT_TABLE;
            case 38:
                return Particle.TRACKING_EMITTER;
            case 39:
                return Particle.NOTE;
            case 40:
                return Particle.WITCH_SPELL;
            case 41:
                return Particle.CARROT;
            case 43:
                return Particle.END_ROD;
            case 44:
                return Particle.RISING_DRAGONS_BREATH;
            case 45:
                return Particle.SPIT;
            case 46:
                return Particle.TOTEM;
            case 47:
                return Particle.FOOD;
            case 48:
                return Particle.FIREWORKS_STARTER;
            case 49:
                return Particle.FIREWORKS_SPARK;
            case 50:
                return Particle.FIREWORKS_OVERLAY;
            case 51:
                return Particle.BALLOON_GAS;
            case 52:
                return Particle.COLORED_FLAME;
            case 53:
                return Particle.SPARKLER;
            case 54:
                return Particle.CONDUIT;
            case 55:
                return Particle.BUBBLE_COLUMN_UP;
            case 56:
                return Particle.BUBBLE_COLUMN_DOWN;
            case 57:
                return Particle.SNEEZE;
            case 60:
                return Particle.LARGE_EXPLOSION;
            case 61:
                return Particle.INK;
            case 62:
                return Particle.FALLING_RED_DUST;
            case 63:
                return Particle.CAMPFIRE_SMOKE;
            case 65:
                return Particle.FALLING_DRAGONS_BREATH;
            case 66:
                return Particle.DRAGONS_BREATH;
            case 67:
                return Particle.BREAK_BLOCK;
            case 68:
                return Particle.PUNCH_BLOCK;
        }

        return null;
    }

}
