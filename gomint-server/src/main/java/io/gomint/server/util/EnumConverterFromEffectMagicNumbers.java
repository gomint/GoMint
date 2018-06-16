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
public class EnumConverterFromEffectMagicNumbers implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return io.gomint.server.entity.potion.EffectMagicNumbers.SPEED;
            case 1:
                return io.gomint.server.entity.potion.EffectMagicNumbers.SLOWNESS;
            case 2:
                return io.gomint.server.entity.potion.EffectMagicNumbers.HASTE;
            case 3:
                return io.gomint.server.entity.potion.EffectMagicNumbers.MINING_FATIGUE;
            case 4:
                return io.gomint.server.entity.potion.EffectMagicNumbers.STRENGTH;
            case 5:
                return io.gomint.server.entity.potion.EffectMagicNumbers.HEALING;
            case 6:
                return io.gomint.server.entity.potion.EffectMagicNumbers.HARMING;
            case 7:
                return io.gomint.server.entity.potion.EffectMagicNumbers.JUMP;
            case 8:
                return io.gomint.server.entity.potion.EffectMagicNumbers.NAUSEA;
            case 9:
                return io.gomint.server.entity.potion.EffectMagicNumbers.REGENERATION;
            case 10:
                return io.gomint.server.entity.potion.EffectMagicNumbers.DAMAGE_RESISTANCE;
            case 11:
                return io.gomint.server.entity.potion.EffectMagicNumbers.FIRE_RESISTANCE;
            case 12:
                return io.gomint.server.entity.potion.EffectMagicNumbers.WATER_BREATHING;
            case 13:
                return io.gomint.server.entity.potion.EffectMagicNumbers.INVISIBILITY;
            case 14:
                return io.gomint.server.entity.potion.EffectMagicNumbers.BLINDNESS;
            case 15:
                return io.gomint.server.entity.potion.EffectMagicNumbers.NIGHT_VISION;
            case 16:
                return io.gomint.server.entity.potion.EffectMagicNumbers.HUNGER;
            case 17:
                return io.gomint.server.entity.potion.EffectMagicNumbers.WEAKNESS;
            case 18:
                return io.gomint.server.entity.potion.EffectMagicNumbers.POISON;
            case 19:
                return io.gomint.server.entity.potion.EffectMagicNumbers.WITHER;
            case 20:
                return io.gomint.server.entity.potion.EffectMagicNumbers.HEALTH_BOOST;
            case 21:
                return io.gomint.server.entity.potion.EffectMagicNumbers.ABSORPTION;
            case 22:
                return io.gomint.server.entity.potion.EffectMagicNumbers.SATURATION;
            case 23:
                return io.gomint.server.entity.potion.EffectMagicNumbers.LEVITATION;
        }

        return null;
    }

}
