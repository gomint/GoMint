/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
import io.gomint.server.entity.EntityType;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.util.random.FastRandom;

import java.util.Collection;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class EntityProjectile extends Entity implements io.gomint.entity.projectile.EntityProjectile {

    protected final EntityLiving shooter;
    private float lastUpdatedT;

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
        this.setHasCollision( false );

        // Gravity
        GRAVITY = 0.04f;
        DRAG = 0.08f;

        this.shooter = shooter;
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

        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= Values.CLIENT_TICK_RATE ) {
            if ( this.hitEntity != null ) {
                this.setPosition( this.hitEntity.getPosition().add( 0, this.hitEntity.getEyeHeight() + this.getHeight(), 0 ) );
            } else {
                Vector position = this.getPosition();
                Vector nextTickMovement = new Vector( this.getPositionX() + this.getMotionX(), this.getPositionY() + this.getMotionY(), this.getPositionZ() + this.getMotionZ() );
                AxisAlignedBB bb = this.boundingBox.addCoordinates( this.getMotionX(), this.getMotionY(), this.getMotionZ() ).grow( 1, 1, 1 );
                Collection<io.gomint.entity.Entity> collidedEntities = this.world.getNearbyEntities( bb, this, null );
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
                                damage += FastRandom.current().nextInt( damage / 2 + 2 );
                            }

                            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent( hitEntity, this, EntityDamageEvent.DamageSource.PROJECTILE, damage );
                            hitEntity.damage( event );

                            // Store entity
                            this.hitEntity = hitEntity;
                        }
                    }
                }
            }

            this.lastUpdatedT = 0;
        }
    }

    @Override
    protected void fall() {

    }

    @Override
    public io.gomint.entity.EntityLiving getShooter() {
        if ( this.shooter.isDead() ) {
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
        position.add(
            -(float) ( Math.cos( position.getYaw() / 180 * Math.PI ) * 0.16f ),
            this.shooter.getEyeHeight() - 0.1f,
            -(float) ( Math.sin( position.getYaw() / 180 * Math.PI ) * 0.16f )
        );

        this.setPosition( position );
        this.setYaw( position.getYaw() );
        this.setPitch( position.getPitch() );

        return position;
    }

}
