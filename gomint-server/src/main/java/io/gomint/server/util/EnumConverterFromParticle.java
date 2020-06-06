/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
                return io.gomint.world.Particle.BUBBLE;
            case 2:
                return io.gomint.world.Particle.CRITICAL;
            case 3:
                return io.gomint.world.Particle.BLOCK_FORCE_FIELD;
            case 4:
                return io.gomint.world.Particle.SMOKE;
            case 5:
                return io.gomint.world.Particle.EXPLODE;
            case 6:
                return io.gomint.world.Particle.EVAPORATION;
            case 7:
                return io.gomint.world.Particle.FLAME;
            case 8:
                return io.gomint.world.Particle.LAVA;
            case 9:
                return io.gomint.world.Particle.LARGE_SMOKE;
            case 10:
                return io.gomint.world.Particle.REDSTONE;
            case 11:
                return io.gomint.world.Particle.RISING_RED_DUST;
            case 12:
                return io.gomint.world.Particle.ITEM_BREAK;
            case 13:
                return io.gomint.world.Particle.SNOWBALL_POOF;
            case 14:
                return io.gomint.world.Particle.HUGE_EXPLODE;
            case 15:
                return io.gomint.world.Particle.HUGE_EXPLODE_SEED;
            case 16:
                return io.gomint.world.Particle.MOB_FLAME;
            case 17:
                return io.gomint.world.Particle.HEART;
            case 18:
                return io.gomint.world.Particle.TERRAIN;
            case 19:
                return io.gomint.world.Particle.SUSPENDED_TOWN;
            case 20:
                return io.gomint.world.Particle.PORTAL;
            case 22:
                return io.gomint.world.Particle.SPLASH;
            case 24:
                return io.gomint.world.Particle.WATER_WAKE;
            case 25:
                return io.gomint.world.Particle.DRIP_WATER;
            case 26:
                return io.gomint.world.Particle.DRIP_LAVA;
            case 27:
                return io.gomint.world.Particle.DRIP_HONEY;
            case 28:
                return io.gomint.world.Particle.FALLING_DUST;
            case 29:
                return io.gomint.world.Particle.MOB_SPELL;
            case 30:
                return io.gomint.world.Particle.MOB_SPELL_AMBIENT;
            case 31:
                return io.gomint.world.Particle.MOB_SPELL_INSTANTANEOUS;
            case 32:
                return io.gomint.world.Particle.NOTE_AND_DUST;
            case 33:
                return io.gomint.world.Particle.SLIME;
            case 34:
                return io.gomint.world.Particle.RAIN_SPLASH;
            case 35:
                return io.gomint.world.Particle.VILLAGER_ANGRY;
            case 36:
                return io.gomint.world.Particle.VILLAGER_HAPPY;
            case 37:
                return io.gomint.world.Particle.ENCHANTMENT_TABLE;
            case 38:
                return io.gomint.world.Particle.TRACKING_EMITTER;
            case 39:
                return io.gomint.world.Particle.NOTE;
            case 40:
                return io.gomint.world.Particle.WITCH_SPELL;
            case 41:
                return io.gomint.world.Particle.CARROT;
            case 43:
                return io.gomint.world.Particle.END_ROD;
            case 44:
                return io.gomint.world.Particle.RISING_DRAGONS_BREATH;
            case 45:
                return io.gomint.world.Particle.SPIT;
            case 46:
                return io.gomint.world.Particle.TOTEM;
            case 47:
                return io.gomint.world.Particle.FOOD;
            case 48:
                return io.gomint.world.Particle.FIREWORKS_STARTER;
            case 49:
                return io.gomint.world.Particle.FIREWORKS_SPARK;
            case 50:
                return io.gomint.world.Particle.FIREWORKS_OVERLAY;
            case 51:
                return io.gomint.world.Particle.BALLOON_GAS;
            case 52:
                return io.gomint.world.Particle.COLORED_FLAME;
            case 53:
                return io.gomint.world.Particle.SPARKLER;
            case 54:
                return io.gomint.world.Particle.CONDUIT;
            case 55:
                return io.gomint.world.Particle.BUBBLE_COLUMN_UP;
            case 56:
                return io.gomint.world.Particle.BUBBLE_COLUMN_DOWN;
            case 57:
                return io.gomint.world.Particle.SNEEZE;
            case 60:
                return io.gomint.world.Particle.LARGE_EXPLOSION;
            case 61:
                return io.gomint.world.Particle.INK;
            case 62:
                return io.gomint.world.Particle.FALLING_RED_DUST;
            case 63:
                return io.gomint.world.Particle.CAMPFIRE_SMOKE;
            case 65:
                return io.gomint.world.Particle.FALLING_DRAGONS_BREATH;
            case 66:
                return io.gomint.world.Particle.DRAGONS_BREATH;
        }

        return null;
    }

}
