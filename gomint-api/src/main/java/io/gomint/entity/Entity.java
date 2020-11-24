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
public interface Entity {

    /**
     * Gets the entity's unqiue identifier.
     *
     * @return The entity's unique identifier
     */
    long getEntityId();

    /**
     * Gets the world the entity resides in.
     *
     * @return The world the entity resides in
     */
    World getWorld();

    /**
     * Gets the location of the entity.
     *
     * @return The entity's location
     */
    Location getLocation();

    /**
     * Get current x axis position
     *
     * @return position on x axis
     */
    float getPositionX();

    /**
     * Get current y axis position
     *
     * @return position on y axis
     */
    float getPositionY();

    /**
     * Get current z axis position
     *
     * @return position on z axis
     */
    float getPositionZ();

    /**
     * Get current pitch
     *
     * @return current pitch
     */
    float getPitch();

    /**
     * Set the entity pitch
     *
     * @param pitch which should be set
     */
    void setPitch( float pitch );

    /**
     * Get current yaw
     *
     * @return current yaw
     */
    float getYaw();

    /**
     * Set yaw of the entity
     *
     * @param yaw which should be set
     */
    void setYaw( float yaw );

    /**
     * Get current head yaw
     *
     * @return current head yaw
     */
    float getHeadYaw();

    /**
     * Set head yaw of the entity
     *
     * @param yaw of the entity
     */
    void setHeadYaw( float yaw );

    /**
     * Set a entities velocity
     *
     * @param velocity to set
     */
    void setVelocity( Vector velocity );

    /**
     * Get current applied velocity
     *
     * @return applied velocity
     */
    Vector getVelocity();

    /**
     * Get the name tag of this entity
     * <p>
     * The name tag is shown above the entity in the client
     *
     * @return The name tag of the entity
     */
    String getNameTag();

    /**
     * Set the name tag of this entity
     * <p>
     * The name tag is shown above the entity in the client
     *
     * @param nameTag The new name tag of this entity
     */
    void setNameTag( String nameTag );

    /**
     * Set name tag to always visible even when not looking at it
     *
     * @param value true for always visible, otherwise false
     */
    void setNameTagAlwaysVisible( boolean value );

    boolean isNameTagAlwaysVisible();

    void setNameTagVisible( boolean value );

    boolean isNameTagVisible();

    AxisAlignedBB getBoundingBox();

    boolean isOnGround();

    /**
     * Get the dead status of this entity
     *
     * @return true when dead, false when alive
     */
    boolean isDead();

    /**
     * Spawn this entity
     *
     * @param location where the entity should spawn
     */
    void spawn( Location location );

    /**
     * Teleport to the given location
     *
     * @param to The location where the entity should be teleported to
     */
    void teleport( Location to );

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
    Vector getDirection();

    /**
     * Get a vector in which direction the entity is looking
     *
     * @return vector which shows in which direction the entity is looking
     */
    Vector2 getDirectionPlane();

    /**
     * Set the age of this entity. This can be used to control automatic despawning.
     *
     * @param duration which will be multiplied with the given unit
     * @param unit     of time
     */
    void setAge( long duration, TimeUnit unit );

    /**
     * Disable ticking of this entity. This causes the given entity to stop moving, it also stops decaying,
     * aging and all the other stuff which requires ticking.
     *
     * @param value true when the entity should tick, false when not
     */
    void setTicking( boolean value );

    /**
     * Check if this entity is currently allow to tick.
     *
     * @return true when ticking, false when not
     */
    boolean isTicking();

    /**
     * Create if needed and return the entities boss bar
     *
     * @return boss bar of this entity
     */
    BossBar getBossBar();

    /**
     * Set the scale of this entity
     *
     * @param scale which should be used (defaults to 1)
     */
    void setScale( float scale );

    /**
     * Get the scale of this entity
     *
     * @return scale of this entity
     */
    float getScale();

    /**
     * Set hidden by default. This decides if the entity is spawned to the players normally or
     * if vision control is done with {@link #showFor(EntityPlayer)} and {@link #hideFor(EntityPlayer)}.
     * This has NO effect on {@link EntityPlayer} entities.
     *
     * @param value true when the server shouldn't broadcast packets for this entity (no movement, spawning etc).
     *              when the value was false and will be set to true all players will despawn the entity, except
     *              those who already have {@link #showFor(EntityPlayer)} called before setting this.
     */
    void setHiddenByDefault( boolean value );

    /**
     * Show this entity for the given player. This only works when {@link #setHiddenByDefault(boolean)} has been set
     * to true.
     * This has NO effect on {@link EntityPlayer} entities.
     *
     * @param player for which this entity should be shown
     */
    void showFor( EntityPlayer player );

    /**
     * Hide this entity for the given player. This only works when {@link #setHiddenByDefault(boolean)} has been set
     * to true.
     * This has NO effect on {@link EntityPlayer} entities.
     *
     * @param player for which this entity should be hidden
     */
    void hideFor( EntityPlayer player );

    /**
     * Get eye height of entity
     *
     * @return eye height
     */
    float getEyeHeight();

    /**
     * Handle a entity interaction from a player
     *
     * @param player the player which has interacted with the entity
     */
    void interact( EntityPlayer player, Vector clickVector );

    /**
     * Reset the fall distance of this entity. This will prevent a entity getting damaged when it hits the ground when
     * it was high enough before (it needs to fall at least 3 blocks to get any damage from falling)
     */
    void resetFallDistance();

    /**
     * Set the entity invisible for others
     *
     * @param value true when this entity should be invisible, false when not
     */
    void setInvisible( boolean value );

    /**
     * Is the entity invisible?
     *
     * @return true if invisible, false if not
     */
    boolean isInvisible();

    /**
     * Set this entity immobile
     *
     * @param value true if immobile, false if not
     */
    void setImmobile( boolean value );

    /**
     * Is the entity immobile?
     *
     * @return true if immobile, false if not
     */
    boolean isImmobile();

    /**
     * Set this entity on fire (it does not get damage, only visual)
     *
     * @param value true for on fire, false for not
     */
    void setOnFire( boolean value );

    /**
     * Check if entity is on fire
     *
     * @return true when on fire, false when not
     */
    boolean isOnFire();

    /**
     * Is this entity affected by gravity?
     *
     * @param value true for yes, false for no
     */
    void setAffectedByGravity( boolean value );

    /**
     * Is this entity affected by gravity?
     *
     * @return true for yes, false for no
     */
    boolean isAffectedByGravity();

    /**
     * Get the fall distance
     *
     * @return current fall distance
     */
    float getFallDistance();

    /**
     * Get the chunk this entity is currently in
     *
     * @return the chunk in which the entity is
     */
    Chunk getChunk();

    /**
     * Get the tags that this entity has
     *
     * @return set of tags this entity has
     */
    Set<String> getTags();

}
