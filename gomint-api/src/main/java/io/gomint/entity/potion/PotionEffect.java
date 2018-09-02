/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.potion;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum PotionEffect {

    SPEED("Speed"),
    SLOWNESS("Slowness"),
    HASTE("Haste"),
    MINING_FATIGUE("Mining Fatigue"),
    STRENGTH("Strength"),
    INSTANT_HEALTH("Healing"),
    INSTANT_DAMAGE("Harming"),
    JUMP_BOOST("Jump Boost"),
    NAUSEA("Nausea"),
    REGENERATION("Regeneration"),
    RESISTANCE("Resistance"),
    FIRE_RESISTANCE("Fire Resistance"),
    WATER_BREATHING("Water Breathing"),
    INVISIBILITY("Invisibility"),
    BLINDNESS("Blindness"),
    NIGHT_VISION("Night Vision"),
    HUNGER("Hunger"),
    WEAKNESS("Weakness"),
    POISON("Poison"),
    WITHER("Wither"),
    HEALTH_BOOST("Health Boost"),
    ABSORPTION("Absorption"),
    SATURATION("Saturation"),
    LEVITATION("Levitation");

    private String effectName;

    PotionEffect( String effectName ) {
        this.effectName = effectName;
    }

    public String getEffectName() {
        return this.effectName;
    }
}
