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
public class EnumConverterFromPotionEffect implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return io.gomint.entity.potion.PotionEffect.SPEED;
            case 1:
                return io.gomint.entity.potion.PotionEffect.SLOWNESS;
            case 2:
                return io.gomint.entity.potion.PotionEffect.HASTE;
            case 3:
                return io.gomint.entity.potion.PotionEffect.MINING_FATIGUE;
            case 4:
                return io.gomint.entity.potion.PotionEffect.STRENGTH;
            case 5:
                return io.gomint.entity.potion.PotionEffect.HEALING;
            case 6:
                return io.gomint.entity.potion.PotionEffect.HARMING;
            case 7:
                return io.gomint.entity.potion.PotionEffect.JUMP;
            case 8:
                return io.gomint.entity.potion.PotionEffect.NAUSEA;
            case 9:
                return io.gomint.entity.potion.PotionEffect.REGENERATION;
            case 10:
                return io.gomint.entity.potion.PotionEffect.DAMAGE_RESISTANCE;
            case 11:
                return io.gomint.entity.potion.PotionEffect.FIRE_RESISTANCE;
            case 12:
                return io.gomint.entity.potion.PotionEffect.WATER_BREATHING;
            case 13:
                return io.gomint.entity.potion.PotionEffect.INVISIBILITY;
            case 14:
                return io.gomint.entity.potion.PotionEffect.BLINDNESS;
            case 15:
                return io.gomint.entity.potion.PotionEffect.NIGHT_VISION;
            case 16:
                return io.gomint.entity.potion.PotionEffect.HUNGER;
            case 17:
                return io.gomint.entity.potion.PotionEffect.WEAKNESS;
            case 18:
                return io.gomint.entity.potion.PotionEffect.POISON;
            case 19:
                return io.gomint.entity.potion.PotionEffect.WITHER;
            case 20:
                return io.gomint.entity.potion.PotionEffect.HEALTH_BOOST;
            case 21:
                return io.gomint.entity.potion.PotionEffect.ABSORPTION;
            case 22:
                return io.gomint.entity.potion.PotionEffect.SATURATION;
            case 23:
                return io.gomint.entity.potion.PotionEffect.LEVITATION;
        }

        return null;
    }

}
