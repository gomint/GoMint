/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.math.Location;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityLiving extends Entity {

    /**
     * Set entity health
     *
     * @param amount of health this entity has
     */
    void setHealth( float amount );

    /**
     * Get the amount of health this entity has
     *
     * @return health of entity
     */
    float getHealth();

    /**
     * Get the maximum amount of health this entity can have
     *
     * @return maximum amount of health this entity can have
     */
    float getMaxHealth();

    /**
     * Set this entity immobile
     *
     * @param value true if immobile, false if not
     */
    void setImmobile( boolean value );

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
     * @param effect which should be applied
     * @param amplifier with which this effect should be calculated
     * @param duration of the effect, will be used in combination with the time unit
     * @param timeUnit which should be used in combination with the duration
     */
    void addEffect( PotionEffect effect, int amplifier, long duration, TimeUnit timeUnit );

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
     * Teleport to the given location
     *
     * @param to The location where the entity should be teleported to
     */
    void teleport( Location to );

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
     * Get the name tag of this entity
     * 
     * The name tag is shown above the entity in the client
     * 
     * @return The name tag of the entity
     */
    String getNameTag();

    /**
     * Set the name tag of this entity
     *
     * The name tag is shown above the entity in the client
     * 
     * @param nameTag The new name tag of this entity
     */
    void setNameTag( String nameTag );

}
