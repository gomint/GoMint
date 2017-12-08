/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.entity;

import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityDamageEvent extends CancellableEntityEvent {

    private final DamageSource damageSource;
    private float damage;

    /**
     * Create a new entity based cancellable event
     *
     * @param entity       for which this event is
     * @param damageSource where the damage comes from
     * @param damage       which should be dealt
     */
    public EntityDamageEvent( Entity entity, DamageSource damageSource, float damage ) {
        super( entity );
        this.damageSource = damageSource;
        this.damage = damage;
    }

    /**
     * Set the damage which should be dealt to the entity
     *
     * @param damage which should be dealt
     */
    public void setDamage( float damage ) {
        this.damage = damage;
    }

    /**
     * Get the damage which should be dealt to the entity
     *
     * @return damage being dealt
     */
    public float getDamage() {
        return this.damage;
    }

    /**
     * Get the source of this damage
     *
     * @return source of damage
     */
    public DamageSource getDamageSource() {
        return this.damageSource;
    }

    public enum DamageSource {

        /**
         * A entity decided to attack
         */
        ENTITY_ATTACK,

        /**
         * Fall damage when falling more than 3 blocks
         */
        FALL,

        /**
         * Damage dealt by the world when you fall under y -64
         */
        VOID,

        /**
         * Hit by a projectile
         */
        PROJECTILE,

        /**
         * When under liquid and no air left
         */
        DROWNING,

        /**
         * When cuddling with a cactus
         */
        CACTUS,

        /**
         * Trying to swim in lava
         */
        LAVA,

        /**
         * On fire?
         */
        ON_FIRE,

        /**
         * Standing in fire
         */
        FIRE,

        /**
         * Damage which will be dealt when a entity explodes
         */
        ENTITY_EXPLODE,

        /**
         * Damage from harm effects
         */
        HARM_EFFECT,

        /**
         * Damage due to hunger
         */
        STARVE,

    }

}
