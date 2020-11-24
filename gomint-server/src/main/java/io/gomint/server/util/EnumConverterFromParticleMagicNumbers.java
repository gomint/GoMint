/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

/**
 * @author generated
 * @version 2.0
 */
public class EnumConverterFromParticleMagicNumbers implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return io.gomint.server.world.ParticleMagicNumbers.BUBBLE;
            case 2:
                return io.gomint.server.world.ParticleMagicNumbers.CRITICAL;
            case 3:
                return io.gomint.server.world.ParticleMagicNumbers.BLOCK_FORCE_FIELD;
            case 4:
                return io.gomint.server.world.ParticleMagicNumbers.SMOKE;
            case 5:
                return io.gomint.server.world.ParticleMagicNumbers.EXPLODE;
            case 6:
                return io.gomint.server.world.ParticleMagicNumbers.EVAPORATION;
            case 7:
                return io.gomint.server.world.ParticleMagicNumbers.FLAME;
            case 8:
                return io.gomint.server.world.ParticleMagicNumbers.LAVA;
            case 9:
                return io.gomint.server.world.ParticleMagicNumbers.LARGE_SMOKE;
            case 10:
                return io.gomint.server.world.ParticleMagicNumbers.REDSTONE;
            case 11:
                return io.gomint.server.world.ParticleMagicNumbers.RISING_RED_DUST;
            case 12:
                return io.gomint.server.world.ParticleMagicNumbers.ITEM_BREAK;
            case 13:
                return io.gomint.server.world.ParticleMagicNumbers.SNOWBALL_POOF;
            case 14:
                return io.gomint.server.world.ParticleMagicNumbers.HUGE_EXPLODE;
            case 15:
                return io.gomint.server.world.ParticleMagicNumbers.HUGE_EXPLODE_SEED;
            case 16:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_FLAME;
            case 17:
                return io.gomint.server.world.ParticleMagicNumbers.HEART;
            case 18:
                return io.gomint.server.world.ParticleMagicNumbers.TERRAIN;
            case 19:
                return io.gomint.server.world.ParticleMagicNumbers.SUSPENDED_TOWN;
            case 20:
                return io.gomint.server.world.ParticleMagicNumbers.PORTAL;
            case 22:
                return io.gomint.server.world.ParticleMagicNumbers.SPLASH;
            case 24:
                return io.gomint.server.world.ParticleMagicNumbers.WATER_WAKE;
            case 25:
                return io.gomint.server.world.ParticleMagicNumbers.DRIP_WATER;
            case 26:
                return io.gomint.server.world.ParticleMagicNumbers.DRIP_LAVA;
            case 27:
                return io.gomint.server.world.ParticleMagicNumbers.DRIP_HONEY;
            case 28:
                return io.gomint.server.world.ParticleMagicNumbers.FALLING_DUST;
            case 29:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_SPELL;
            case 30:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_SPELL_AMBIENT;
            case 31:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_SPELL_INSTANTANEOUS;
            case 32:
                return io.gomint.server.world.ParticleMagicNumbers.NOTE_AND_DUST;
            case 33:
                return io.gomint.server.world.ParticleMagicNumbers.SLIME;
            case 34:
                return io.gomint.server.world.ParticleMagicNumbers.RAIN_SPLASH;
            case 35:
                return io.gomint.server.world.ParticleMagicNumbers.VILLAGER_ANGRY;
            case 36:
                return io.gomint.server.world.ParticleMagicNumbers.VILLAGER_HAPPY;
            case 37:
                return io.gomint.server.world.ParticleMagicNumbers.ENCHANTMENT_TABLE;
            case 38:
                return io.gomint.server.world.ParticleMagicNumbers.TRACKING_EMITTER;
            case 39:
                return io.gomint.server.world.ParticleMagicNumbers.NOTE;
            case 40:
                return io.gomint.server.world.ParticleMagicNumbers.WITCH_SPELL;
            case 41:
                return io.gomint.server.world.ParticleMagicNumbers.CARROT;
            case 43:
                return io.gomint.server.world.ParticleMagicNumbers.END_ROD;
            case 44:
                return io.gomint.server.world.ParticleMagicNumbers.RISING_DRAGONS_BREATH;
            case 45:
                return io.gomint.server.world.ParticleMagicNumbers.SPIT;
            case 46:
                return io.gomint.server.world.ParticleMagicNumbers.TOTEM;
            case 47:
                return io.gomint.server.world.ParticleMagicNumbers.FOOD;
            case 48:
                return io.gomint.server.world.ParticleMagicNumbers.FIREWORKS_STARTER;
            case 49:
                return io.gomint.server.world.ParticleMagicNumbers.FIREWORKS_SPARK;
            case 50:
                return io.gomint.server.world.ParticleMagicNumbers.FIREWORKS_OVERLAY;
            case 51:
                return io.gomint.server.world.ParticleMagicNumbers.BALLOON_GAS;
            case 52:
                return io.gomint.server.world.ParticleMagicNumbers.COLORED_FLAME;
            case 53:
                return io.gomint.server.world.ParticleMagicNumbers.SPARKLER;
            case 54:
                return io.gomint.server.world.ParticleMagicNumbers.CONDUIT;
            case 55:
                return io.gomint.server.world.ParticleMagicNumbers.BUBBLE_COLUMN_UP;
            case 56:
                return io.gomint.server.world.ParticleMagicNumbers.BUBBLE_COLUMN_DOWN;
            case 57:
                return io.gomint.server.world.ParticleMagicNumbers.SNEEZE;
            case 60:
                return io.gomint.server.world.ParticleMagicNumbers.LARGE_EXPLOSION;
            case 61:
                return io.gomint.server.world.ParticleMagicNumbers.INK;
            case 62:
                return io.gomint.server.world.ParticleMagicNumbers.FALLING_RED_DUST;
            case 63:
                return io.gomint.server.world.ParticleMagicNumbers.CAMPFIRE_SMOKE;
            case 65:
                return io.gomint.server.world.ParticleMagicNumbers.FALLING_DRAGONS_BREATH;
            case 66:
                return io.gomint.server.world.ParticleMagicNumbers.DRAGONS_BREATH;
            case 67:
                return io.gomint.server.world.ParticleMagicNumbers.BREAK_BLOCK;
            case 68:
                return io.gomint.server.world.ParticleMagicNumbers.PUNCH_BLOCK;
        }

        return null;
    }

}
