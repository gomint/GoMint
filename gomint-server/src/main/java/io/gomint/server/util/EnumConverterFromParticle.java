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
public class EnumConverterFromParticle implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return io.gomint.world.Particle.BUBBLE;
            case 1:
                return io.gomint.world.Particle.CRITICAL;
            case 2:
                return io.gomint.world.Particle.BLOCK_FORCE_FIELD;
            case 3:
                return io.gomint.world.Particle.SMOKE;
            case 4:
                return io.gomint.world.Particle.EXPLODE;
            case 5:
                return io.gomint.world.Particle.EVAPORATION;
            case 6:
                return io.gomint.world.Particle.FLAME;
            case 7:
                return io.gomint.world.Particle.LAVA;
            case 8:
                return io.gomint.world.Particle.LARGE_SMOKE;
            case 9:
                return io.gomint.world.Particle.REDSTONE;
            case 10:
                return io.gomint.world.Particle.RISING_RED_DUST;
            case 11:
                return io.gomint.world.Particle.ITEM_BREAK;
            case 12:
                return io.gomint.world.Particle.SNOWBALL_POOF;
            case 13:
                return io.gomint.world.Particle.HUGE_EXPLODE;
            case 14:
                return io.gomint.world.Particle.HUGE_EXPLODE_SEED;
            case 15:
                return io.gomint.world.Particle.MOB_FLAME;
            case 16:
                return io.gomint.world.Particle.HEART;
            case 17:
                return io.gomint.world.Particle.TERRAIN;
            case 18:
                return io.gomint.world.Particle.SUSPENDED_TOWN;
            case 19:
                return io.gomint.world.Particle.PORTAL;
            case 20:
                return io.gomint.world.Particle.SPLASH;
            case 21:
                return io.gomint.world.Particle.WATER_WAKE;
            case 22:
                return io.gomint.world.Particle.DRIP_WATER;
            case 23:
                return io.gomint.world.Particle.DRIP_LAVA;
            case 24:
                return io.gomint.world.Particle.FALLING_DUST;
            case 25:
                return io.gomint.world.Particle.MOB_SPELL;
            case 26:
                return io.gomint.world.Particle.MOB_SPELL_AMBIENT;
            case 27:
                return io.gomint.world.Particle.MOB_SPELL_INSTANTANEOUS;
            case 28:
                return io.gomint.world.Particle.INK;
            case 29:
                return io.gomint.world.Particle.SLIME;
            case 30:
                return io.gomint.world.Particle.RAIN_SPLASH;
            case 31:
                return io.gomint.world.Particle.VILLAGER_ANGRY;
            case 32:
                return io.gomint.world.Particle.VILLAGER_HAPPY;
            case 33:
                return io.gomint.world.Particle.ENCHANTMENT_TABLE;
            case 34:
                return io.gomint.world.Particle.TRACKING_EMITTER;
            case 35:
                return io.gomint.world.Particle.NOTE;
            case 36:
                return io.gomint.world.Particle.WITCH_SPELL;
            case 37:
                return io.gomint.world.Particle.CARROT;
            case 38:
                return io.gomint.world.Particle.END_ROD;
            case 39:
                return io.gomint.world.Particle.DRAGONS_BREATH;
            case 40:
                return io.gomint.world.Particle.BREAK_BLOCK;
            case 41:
                return io.gomint.world.Particle.PUNCH_BLOCK;
        }

        return null;
    }

}
