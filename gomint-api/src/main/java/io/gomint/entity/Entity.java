/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.math.Vector2;
import io.gomint.world.Chunk;
import io.gomint.world.World;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Entity<E> {

    /**
     * Gets the entity's unqiue identifier.
     *
     * @return The entity's unique identifier
     */
    long id();

    /**
     * Gets the world the entity resides in.
     *
     * @return The world the entity resides in
     */
    World world();

    /**
     * Gets the location of the entity.
     *
     * @return The entity's location
     */
    Location location();

    /**
     * Get current x axis position
     *
     * @return position on x axis
     */
    float positionX();

    /**
     * Get current y axis position
     *
     * @return position on y axis
     */
    float positionY();

    /**
     * Get current z axis position
     *
     * @return position on z axis
     */
    float positionZ();

    /**
     * Get current pitch
     *
     * @return current pitch
     */
    float pitch();

    /**
     * Set the entity pitch
     *
     * @param pitch which should be set
     * @return entity for chaining
     */
    E pitch(float pitch);

    /**
     * Get current yaw
     *
     * @return current yaw
     */
    float yaw();

    /**
     * Set yaw of the entity
     *
     * @param yaw which should be set
     * @return entity for chaining
     */
    E yaw(float yaw);

    /**
     * Get current head yaw
     *
     * @return current head yaw
     */
    float headYaw();

    /**
     * Set head yaw of the entity
     *
     * @param yaw of the entity
     * @return entity for chaining
     */
    E headYaw(float yaw);

    /**
     * Set a entities velocity
     *
     * @param velocity to set
     * @return entity for chaining
     */
    E velocity(Vector velocity);

    /**
     * Get current applied velocity
     *
     * @return applied velocity
     */
    Vector velocity();

    /**
     * Get the name tag of this entity
     * <p>
     * The name tag is shown above the entity in the client
     *
     * @return The name tag of the entity
     */
    String nameTag();

    /**
     * Set the name tag of this entity
     * <p>
     * The name tag is shown above the entity in the client
     *
     * @param nameTag The new name tag of this entity
     * @return entity for chaining
     */
    E nameTag(String nameTag);

    /**
     * Set name tag to always visible even when not looking at it
     *
     * @param value true for always visible, otherwise false
     * @return entity for chaining
     */
    E nameTagAlwaysVisible(boolean value);

    boolean nameTagAlwaysVisible();

    /**
     * Set name tags to be visible
     *
     * @param value true for visible, otherwise false
     * @return entity for chaining
     */
    E nameTagVisible(boolean value);

    boolean nameTagVisible();

    AxisAlignedBB boundingBox();

    boolean onGround();

    /**
     * Get the dead status of this entity
     *
     * @return true when dead, false when alive
     */
    boolean dead();

    /**
     * Spawn this entity
     *
     * @param location where the entity should spawn
     * @return entity for chaining
     */
    E spawn(Location location);

    /**
     * Teleport to the given location
     *
     * @param to The location where the entity should be teleported to
     * @return entity for chaining
     */
    E teleport(Location to);

    /**
     * Despawn this entity on the next tick
     */
    void despawn();

    /**
     * Gets the direction the entity's body is facing as a normalized vector.
     * Note, though, that pitch rotation is considered to be part of the entity's
     * head and is thus not included inside the vector returned by this function.
     *
     * @return The direction vector the entity's body is facing
     */
    Vector direction();

    /**
     * Get a vector in which direction the entity is looking
     *
     * @return vector which shows in which direction the entity is looking
     */
    Vector2 directionPlane();

    /**
     * Set the age of this entity. This can be used to control automatic despawning.
     *
     * @param duration which will be multiplied with the given unit
     * @param unit     of time
     *                 @return entity for chaining
     */
    E age(long duration, TimeUnit unit);

    /**
     * Disable ticking of this entity. This causes the given entity to stop moving, it also stops decaying,
     * aging and all the other stuff which requires ticking.
     *
     * @param value true when the entity should tick, false when not
     *              @return entity for chaining
     */
    E ticking(boolean value);

    /**
     * Check if this entity is currently allow to tick.
     *
     * @return true when ticking, false when not
     */
    boolean ticking();

    /**
     * Create if needed and return the entities boss bar
     *
     * @return boss bar of this entity
     */
    BossBar bossBar();

    /**
     * Set the scale of this entity
     *
     * @param scale which should be used (defaults to 1)
     *              @return entity for chaining
     */
    E scale(float scale);

    /**
     * Get the scale of this entity
     *
     * @return scale of this entity
     */
    float scale();

    /**
     * Set hidden by default. This decides if the entity is spawned to the players normally or
     * if vision control is done with {@link #showFor(EntityPlayer)} and {@link #hideFor(EntityPlayer)}.
     * This has NO effect on {@link EntityPlayer} entities.
     *
     * @param value true when the server shouldn't broadcast packets for this entity (no movement, spawning etc).
     *              when the value was false and will be set to true all players will despawn the entity, except
     *              those who already have {@link #showFor(EntityPlayer)} called before setting this.
     *              @return entity for chaining
     */
    E hiddenByDefault(boolean value);

    /**
     * Show this entity for the given player. This only works when {@link #hiddenByDefault(boolean)} has been set
     * to true.
     * This has NO effect on {@link EntityPlayer} entities.
     *
     * @param player for which this entity should be shown
     *               @return entity for chaining
     */
    E showFor(EntityPlayer player);

    /**
     * Hide this entity for the given player. This only works when {@link #hiddenByDefault(boolean)} has been set
     * to true.
     * This has NO effect on {@link EntityPlayer} entities.
     *
     * @param player for which this entity should be hidden
     *               @return entity for chaining
     */
    E hideFor(EntityPlayer player);

    /**
     * Get eye height of entity
     *
     * @return eye height
     */
    float eyeHeight();

    /**
     * Handle a entity interaction from a player
     *
     * @param player      the player which has interacted with the entity
     * @param clickVector position where this entity has been interacted at
     *                    @return entity for chaining
     */
    E interact(EntityPlayer player, Vector clickVector);

    /**
     * Reset the fall distance of this entity. This will prevent a entity getting damaged when it hits the ground when
     * it was high enough before (it needs to fall at least 3 blocks to get any damage from falling)
     *
     * @return entity for chaining
     */
    E resetFallDistance();

    /**
     * Set the entity invisible for others
     *
     * @param value true when this entity should be invisible, false when not
     *              @return entity for chaining
     */
    E invisible(boolean value);

    /**
     * Is the entity invisible?
     *
     * @return true if invisible, false if not
     */
    boolean invisible();

    /**
     * Set this entity immobile
     *
     * @param value true if immobile, false if not
     *              @return entity for chaining
     */
    E immobile(boolean value);

    /**
     * Is the entity immobile?
     *
     * @return true if immobile, false if not
     */
    boolean immobile();

    /**
     * Does this entity has a active collision box?
     *
     * @param value true when collision is active, false when not
     * @return entity for chaining
     */
    E collision(boolean value);

    /**
     * Does this entity has a active collision box?
     *
     * @return true when collisions are active, false when not
     */
    boolean collision();

    /**
     * Set this entity on fire (it does not get damage, only visual)
     *
     * @param value true for on fire, false for not
     *              @return entity for chaining
     */
    E burning(boolean value);

    /**
     * Check if entity is on fire
     *
     * @return true when on fire, false when not
     */
    boolean burning();

    /**
     * Is this entity affected by gravity?
     *
     * @param value true for yes, false for no
     *              @return entity for chaining
     */
    E affectedByGravity(boolean value);

    /**
     * Is this entity affected by gravity?
     *
     * @return true for yes, false for no
     */
    boolean affectedByGravity();

    /**
     * Get the fall distance
     *
     * @return current fall distance
     */
    float fallDistance();

    /**
     * Get the chunk this entity is currently in
     *
     * @return the chunk in which the entity is
     */
    Chunk chunk();

    /**
     * Get the tags that this entity has
     *
     * @return set of tags this entity has
     */
    Set<String> tags();

}
