/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

import io.gomint.entity.potion.Effect;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityLiving extends Entity {

    /**
     * Set entity health
     *
     * @param amount of health this entity has. setting to lower or equal to 0 kills the entity
     */
    void setHealth( float amount );

    /**
     * Get the amount of health this entity has
     *
     * @return health of entity
     */
    float getHealth();

    /**
     * Set the maximum amount of health this entity can have
     *
     * @param amount of health this entity can have as a max
     */
    void setMaxHealth( float amount );

    /**
     * Get the maximum amount of health this entity can have
     *
     * @return maximum amount of health this entity can have
     */
    float getMaxHealth();

    /**
     * Get the entities last damage source
     *
     * @return damage soruce or null when not damaged
     */
    EntityDamageEvent.DamageSource getLastDamageSource();

    /**
     * Get the entity which dealt the last damage
     *
     * @return null when {@link #getLastDamageSource()} is not {@link io.gomint.event.entity.EntityDamageEvent.DamageSource#ENTITY_ATTACK}
     * or {@link io.gomint.event.entity.EntityDamageEvent.DamageSource#PROJECTILE} or the entity has already been despawned
     */
    Entity getLastDamageEntity();

    /**
     * Set the amount of damage which can be absorbed
     *
     * @param amount of damage which should be absorbed
     */
    void setAbsorptionHearts( float amount );

    /**
     * Get the amount of damage which can be absorbed
     *
     * @return amount of damage which can be absorbed
     */
    float getAbsorptionHearts();

    /**
     * Add a new effect to the player. If the player already has a effect active the newer one gets taken.
     *
     * @param effect    which should be applied
     * @param amplifier with which this effect should be calculated
     * @param duration  of the effect, will be used in combination with the time unit
     * @param timeUnit  which should be used in combination with the duration
     * @return the added effect
     */
    Effect addEffect( PotionEffect effect, int amplifier, long duration, TimeUnit timeUnit );

    /**
     * Does a player have the given effect?
     *
     * @param effect which should be checked for
     * @return true when the player has the effect, false when not
     */
    boolean hasEffect( PotionEffect effect );

    /**
     * Get the effect amplifier
     *
     * @param effect for which we want to know the amplifier
     * @return amplifier of effect or -1 if effect is not active
     */
    int getEffectAmplifier( PotionEffect effect );

    /**
     * Remove the given effect from the player
     *
     * @param effect which should be removed
     */
    void removeEffect( PotionEffect effect );

    /**
     * Remove all effects from this entity
     */
    void removeAllEffects();

    /**
     * Get the movement speed of this entity
     *
     * @return movement speed
     */
    float getMovementSpeed();

    /**
     * Set movement speed of this entity
     *
     * @param value of the new movement speed
     */
    void setMovementSpeed( float value );

    /**
     * Attack given entity
     *
     * @param damage amount which should be dealt
     * @param source of the damage, controls armor calcs etc.
     */
    void attack( float damage, EntityDamageEvent.DamageSource source );

    /**
     * Set entity on fire for given amount of seconds
     *
     * @param duration for how long this entity should be on fire
     * @param unit     with which the duration should be multiplied
     */
    void setBurning( long duration, TimeUnit unit );

    /**
     * Extinguish this entity
     */
    void extinguish();

}
