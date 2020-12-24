/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.event.entity.EntityCollisionWithEntityEvent;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.projectile.ProjectileHitEntityEvent;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class EntityProjectile extends Entity implements io.gomint.entity.projectile.EntityProjectile {

    protected final EntityLiving shooter;
    private float lastUpdateDT;

    // Hit state tracking
    protected Entity hitEntity;

    /**
     * Construct a new Entity
     *
     * @param shooter of this entity
     * @param type    The type of the Entity
     * @param world   The world in which this entity is in
     */
    protected EntityProjectile( EntityLiving shooter, EntityType type, WorldAdapter world ) {
        super( type, world );
        this.shooter = shooter;
    }

    @Override
    protected void applyCustomProperties() {
        super.applyCustomProperties();

        // Collisions
        this.setHasCollision( false );
    }

    public abstract boolean isCritical();
    public abstract float getDamage();

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        // Reset hit entity on death
        if ( this.hitEntity != null && this.hitEntity.isDead() ) {
            this.hitEntity = null;
        }

        this.lastUpdateDT += dT;
        if ( Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON ) {
            if ( this.hitEntity != null ) {
                this.setPosition( this.hitEntity.getPosition().add( 0, this.hitEntity.getEyeHeight() + this.getHeight(), 0 ) );
            } else {
                Vector position = this.getPosition();
                Vector nextTickMovement = new Vector( this.getPositionX() + this.getMotionX(), this.getPositionY() + this.getMotionY(), this.getPositionZ() + this.getMotionZ() );
                AxisAlignedBB bb = this.boundingBox.addCoordinates( this.getMotionX(), this.getMotionY(), this.getMotionZ() ).grow( 1, 1, 1 );
                Collection<io.gomint.entity.Entity> collidedEntities = this.world.getNearbyEntities( bb, this );
                if ( collidedEntities != null ) {
                    double savedDistance = 0.0D;
                    Entity hitEntity = null;

                    for ( io.gomint.entity.Entity collidedEntity : collidedEntities ) {
                        Entity implEntity = (Entity) collidedEntity;

                        // Does this entity support colliding?
                        if ( !implEntity.hasCollision() ) {
                            continue;
                        }

                        // Skip own entity until we moved far enough
                        if ( collidedEntity.equals( this.shooter ) && this.age < 5 ) {
                            continue;
                        }

                        // Check for spectator game mode / no clip
                        if ( collidedEntity instanceof EntityPlayer ) {
                            EntityPlayer otherPlayer = (EntityPlayer) collidedEntity;
                            if ( otherPlayer.getAdventureSettings().isNoClip() ) {
                                continue;
                            }
                        }

                        // Check if entity intercepts with next movement
                        AxisAlignedBB entityBB = collidedEntity.getBoundingBox().grow( 0.3f, 0.3f, 0.3f );
                        Vector onLineVector = entityBB.calculateIntercept( position, nextTickMovement );
                        if ( onLineVector == null ) {
                            continue;
                        }

                        // Event to check for custom collision detection
                        EntityCollisionWithEntityEvent event = new EntityCollisionWithEntityEvent( collidedEntity, this );
                        this.world.getServer().getPluginManager().callEvent( event );

                        if ( !event.isCancelled() ) {
                            double currentDistance = position.distanceSquared( onLineVector );
                            if ( currentDistance < savedDistance || savedDistance == 0.0 ) {
                                hitEntity = (Entity) collidedEntity;
                                savedDistance = currentDistance;
                            }
                        }
                    }

                    // Check if we hit a entity
                    if ( hitEntity != null ) {
                        // Event
                        ProjectileHitEntityEvent entityEvent = new ProjectileHitEntityEvent( hitEntity, this );
                        this.getWorld().getServer().getPluginManager().callEvent( entityEvent );

                        if ( !entityEvent.isCancelled() ) {
                            // Calculate damage
                            float motion = (float) Math.sqrt( MathUtils.square( this.getMotionX() ) + MathUtils.square( this.getMotionY() ) + MathUtils.square( this.getMotionZ() ) );
                            int damage = MathUtils.fastCeil( motion * getDamage() );

                            // Critical?
                            if ( isCritical() ) {
                                damage += ThreadLocalRandom.current().nextInt( damage / 2 + 2 );
                            }

                            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent( hitEntity, this, EntityDamageEvent.DamageSource.PROJECTILE, damage );
                            if ( hitEntity.damage( event ) ) {
                                this.applyCustomKnockback( hitEntity );
                                this.applyCustomDamageEffects( hitEntity );
                            }

                            // Store entity
                            this.hitEntity = hitEntity;
                        }
                    }
                }
            }

            this.lastUpdateDT = 0;
        }
    }

    /**
     * Apply custom effects for a entity which has been hit by the projectile
     *
     * @param hitEntity which has been hit
     */
    protected void applyCustomDamageEffects( Entity hitEntity ) {

    }

    /**
     * This method should be overwritten by projectiles which can alter the hit entities motion (like punch arrows)
     *
     * @param hitEntity which has been hit
     */
    protected void applyCustomKnockback( Entity hitEntity ) {

    }

    @Override
    protected void fall() {

    }

    @Override
    public io.gomint.entity.EntityLiving getShooter() {
        if ( this.shooter == null || this.shooter.isDead() ) {
            return null;
        }

        return this.shooter;
    }

    /**
     * Set the position based on the position of the shooter
     */
    Location setPositionFromShooter() {
        // Calculate starting position
        Location position = this.shooter.getLocation();

        this.setPosition( position.add(
            0,
            this.shooter.getEyeHeight() - 0.1f,
            0
        ) );

        this.setYaw( position.getYaw() );
        this.setPitch( position.getPitch() );

        return position;
    }

    /**
     * Set yaw and pitch from current motion
     */
    protected void setLookFromMotion() {
        // Calculate correct yaw / pitch
        double motionDistance = MathUtils.square( this.getMotionX() ) + MathUtils.square( this.getMotionZ() );
        float motionForce = (float) Math.sqrt( motionDistance );

        float yaw = (float) ( Math.atan2( this.getMotionX(), this.getMotionZ() ) * 180.0D / Math.PI );
        float pitch = (float) ( Math.atan2( this.getMotionY(), motionForce ) * 180.0D / Math.PI );

        this.setYaw( yaw );
        this.setHeadYaw( yaw );
        this.setPitch( pitch );
    }

    protected void setMotionFromEntity(Location position, Vector motion, float pitchOffset, float velocity, float inaccuracy) {
        Vector newMotion = new Vector(
            (float) (-Math.sin(position.getYaw() * 0.0175f) * Math.cos(position.getPitch() * 0.0175f)),
            (float) -Math.sin((position.getPitch() + pitchOffset) * 0.0175f),
            (float) (Math.cos(position.getYaw() * 0.0175f) * Math.cos(position.getPitch() * 0.0175f))
        );

        this.setMotionFromHeading(newMotion.add(motion), velocity, inaccuracy);
    }

    protected void setMotionFromPosition(Location position, float pitchOffset, float velocity, float inaccuracy) {
        Vector motion = new Vector(
            (float) (-Math.sin(position.getYaw() * 0.0175f) * Math.cos(position.getPitch() * 0.0175f)),
            (float) -Math.sin((position.getPitch() + pitchOffset) * 0.0175f),
            (float) (Math.cos(position.getYaw() * 0.0175f) * Math.cos(position.getPitch() * 0.0175f))
        );

        this.setMotionFromHeading(motion, velocity, inaccuracy);
    }

    /**
     * Set the motion from the heading vector
     *
     * @param motion
     * @param velocity
     * @param inaccuracy
     */
    protected void setMotionFromHeading(Vector motion, float velocity, float inaccuracy) {
        float distanceTravel = (float) Math.sqrt(MathUtils.square(motion.getX()) + MathUtils.square(motion.getY()) + MathUtils.square(motion.getZ()));
        motion.setX(((float) (((motion.getX() / distanceTravel) + (ThreadLocalRandom.current().nextDouble() * 0.0075f)) * inaccuracy)) * velocity);
        motion.setY(((float) (((motion.getY() / distanceTravel) + (ThreadLocalRandom.current().nextDouble() * 0.0075f)) * inaccuracy)) * velocity);
        motion.setZ(((float) (((motion.getZ() / distanceTravel) + (ThreadLocalRandom.current().nextDouble() * 0.0075f)) * inaccuracy)) * velocity);
        this.setVelocity(motion);
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.PROJECTILE;
    }

}
