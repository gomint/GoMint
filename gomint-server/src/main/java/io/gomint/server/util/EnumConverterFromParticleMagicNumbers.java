/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
            case 1:
                return io.gomint.server.world.ParticleMagicNumbers.CRITICAL;
            case 2:
                return io.gomint.server.world.ParticleMagicNumbers.BLOCK_FORCE_FIELD;
            case 3:
                return io.gomint.server.world.ParticleMagicNumbers.SMOKE;
            case 4:
                return io.gomint.server.world.ParticleMagicNumbers.EXPLODE;
            case 5:
                return io.gomint.server.world.ParticleMagicNumbers.EVAPORATION;
            case 6:
                return io.gomint.server.world.ParticleMagicNumbers.FLAME;
            case 7:
                return io.gomint.server.world.ParticleMagicNumbers.LAVA;
            case 8:
                return io.gomint.server.world.ParticleMagicNumbers.LARGE_SMOKE;
            case 9:
                return io.gomint.server.world.ParticleMagicNumbers.REDSTONE;
            case 10:
                return io.gomint.server.world.ParticleMagicNumbers.RISING_RED_DUST;
            case 11:
                return io.gomint.server.world.ParticleMagicNumbers.ITEM_BREAK;
            case 12:
                return io.gomint.server.world.ParticleMagicNumbers.SNOWBALL_POOF;
            case 13:
                return io.gomint.server.world.ParticleMagicNumbers.HUGE_EXPLODE;
            case 14:
                return io.gomint.server.world.ParticleMagicNumbers.HUGE_EXPLODE_SEED;
            case 15:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_FLAME;
            case 16:
                return io.gomint.server.world.ParticleMagicNumbers.HEART;
            case 17:
                return io.gomint.server.world.ParticleMagicNumbers.TERRAIN;
            case 18:
                return io.gomint.server.world.ParticleMagicNumbers.SUSPENDED_TOWN;
            case 19:
                return io.gomint.server.world.ParticleMagicNumbers.PORTAL;
            case 20:
                return io.gomint.server.world.ParticleMagicNumbers.SPLASH;
            case 21:
                return io.gomint.server.world.ParticleMagicNumbers.WATER_WAKE;
            case 22:
                return io.gomint.server.world.ParticleMagicNumbers.DRIP_WATER;
            case 23:
                return io.gomint.server.world.ParticleMagicNumbers.DRIP_LAVA;
            case 24:
                return io.gomint.server.world.ParticleMagicNumbers.FALLING_DUST;
            case 25:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_SPELL;
            case 26:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_SPELL_AMBIENT;
            case 27:
                return io.gomint.server.world.ParticleMagicNumbers.MOB_SPELL_INSTANTANEOUS;
            case 28:
                return io.gomint.server.world.ParticleMagicNumbers.INK;
            case 29:
                return io.gomint.server.world.ParticleMagicNumbers.SLIME;
            case 30:
                return io.gomint.server.world.ParticleMagicNumbers.RAIN_SPLASH;
            case 31:
                return io.gomint.server.world.ParticleMagicNumbers.VILLAGER_ANGRY;
            case 32:
                return io.gomint.server.world.ParticleMagicNumbers.VILLAGER_HAPPY;
            case 33:
                return io.gomint.server.world.ParticleMagicNumbers.ENCHANTMENT_TABLE;
            case 34:
                return io.gomint.server.world.ParticleMagicNumbers.TRACKING_EMITTER;
            case 35:
                return io.gomint.server.world.ParticleMagicNumbers.NOTE;
            case 36:
                return io.gomint.server.world.ParticleMagicNumbers.WITCH_SPELL;
            case 37:
                return io.gomint.server.world.ParticleMagicNumbers.CARROT;
            case 38:
                return io.gomint.server.world.ParticleMagicNumbers.END_ROD;
            case 39:
                return io.gomint.server.world.ParticleMagicNumbers.DRAGONS_BREATH;
            case 40:
                return io.gomint.server.world.ParticleMagicNumbers.BREAK_BLOCK;
            case 41:
                return io.gomint.server.world.ParticleMagicNumbers.PUNCH_BLOCK;
        }

        return null;
    }

}
