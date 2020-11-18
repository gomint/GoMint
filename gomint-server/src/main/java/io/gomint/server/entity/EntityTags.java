/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityTags {

    /**
     * Tag which checks if the entity is automatically spawnable
     */
    public static final String TAG_SPAWNABLE = "spawnable";

    /**
     * Tag which checks if the entity can attack with a melee weapon
     */
    public static final String TAG_MELEE = "melee";

    /**
     * Tag which checks if the entity is a mob
     */
    public static final String TAG_MOB = "mob";

    /**
     * Tag which checks if the entity is hostile towards others
     */
    public static final String TAG_HOSTILE = "hostile";

    /**
     * Tag which checks if the entity is living
     */
    public static final String TAG_LIVING = "living";

    /**
     * Tag which checks if the entity can damage others
     */
    public static final String TAG_DAMAGING = "damaging";

    /**
     * Tag which checks if the entity has a ranged attack
     */
    public static final String TAG_RANGED = "ranged";

    /**
     * Tag which checks if the entity is a player
     */
    public static final String TAG_PLAYER = "player";

    /**
     * Tag which checks if the entity is a human (NPC)
     */
    public static final String TAG_HUMAN = "human";

    /**
     * Tag which checks if the entity is a projectile
     */
    public static final String TAG_PROJECTILE = "projectile";

    /**
     * Tag which checks if the entity is a passive one
     */
    public static final String TAG_PASSIVE = "passive";

    /**
     * Tag which checks if the entity is a animal
     */
    public static final String TAG_ANIMAL = "animal";

    /**
     * Tag which checks if the entity is ageable
     */
    public static final String TAG_AGEABLE = "ageable";

    /**
     * Tag which checks if the entity is a creature
     */
    public static final String TAG_CREATURE = "creature";

    /**
     * Tag which checks if the entity is a water creature
     */
    public static final String TAG_WATER_CREATURE = "water_creature";

    /**
     * Tags a mob (hostile) should have
     */
    public static final Set<String> HOSTILE_MOB = Set.of(TAG_MOB, TAG_HOSTILE, TAG_LIVING, TAG_DAMAGING, TAG_MELEE, TAG_SPAWNABLE);

    /**
     * Tags a mob (hostile) which has a ranged attack
     */
    public static final Set<String> RANGED_HOSTILE_MOB = Set.of(TAG_MOB, TAG_HOSTILE, TAG_LIVING, TAG_DAMAGING, TAG_RANGED, TAG_MELEE, TAG_SPAWNABLE);

    /**
     * Tags a player has
     */
    public static final Set<String> PLAYER = Set.of(TAG_PLAYER, TAG_HOSTILE, TAG_LIVING, TAG_DAMAGING, TAG_RANGED, TAG_MELEE);

    /**
     * Tags a human has
     */
    public static final Set<String> HUMAN = Set.of(TAG_HUMAN, TAG_HOSTILE, TAG_LIVING, TAG_DAMAGING, TAG_RANGED, TAG_MELEE);

    /**
     * Tags a projectile has
     */
    public static final Set<String> PROJECTILE = Set.of(TAG_PROJECTILE);

    /**
     * Tags a passive entity has
     */
    public static final Set<String> PASSIVE = Set.of(TAG_PASSIVE);

    /**
     * Tags a animal has
     */
    public static final Set<String> ANIMAL = Set.of(TAG_ANIMAL, TAG_LIVING, TAG_SPAWNABLE);

    /**
     * Tags a ageable animal has
     */
    public static final Set<String> AGEABLE_ANIMAL = Set.of(TAG_ANIMAL, TAG_LIVING, TAG_AGEABLE, TAG_SPAWNABLE);

    /**
     * Tags a simple creature has
     */
    public static final Set<String> CREATURE = Set.of(TAG_CREATURE, TAG_LIVING, TAG_SPAWNABLE);

    /**
     * Tags a water creature has
     */
    public static final Set<String> WATER_CREATURE = Set.of(TAG_WATER_CREATURE, TAG_LIVING, TAG_SPAWNABLE);

}
