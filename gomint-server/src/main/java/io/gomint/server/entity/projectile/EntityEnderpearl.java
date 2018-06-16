/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityTeleportEvent;
import io.gomint.event.entity.projectile.ProjectileHitBlocksEvent;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityEnderpearl extends EntityProjectile implements io.gomint.entity.projectile.EntityEnderpearl {

    private float lastUpdatedT;

    /**
     * Construct a new Entity
     *
     * @param player which spawned this hook
     * @param world  The world in which this entity is in
     */
    public EntityEnderpearl( EntityPlayer player, WorldAdapter world ) {
        super( player, EntityType.THROWN_ENDERPEARL, world );

        // Gravity
        GRAVITY = 0.03f;
        DRAG = 0.01f;

        this.setSize( 0.25f, 0.25f );

        // Calculate starting position
        Location position = this.setPositionFromShooter();

        // Calculate motion
        Vector motion = new Vector(
            (float) ( -Math.sin( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( -Math.sin( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( Math.cos( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f )
        );

        float distanceTravel = (float) Math.sqrt( MathUtils.square( motion.getX() ) + MathUtils.square( motion.getY() ) + MathUtils.square( motion.getZ() ) );
        motion.setX( (float) ( ( ( motion.getX() / distanceTravel ) + ( FastRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        motion.setY( (float) ( ( ( motion.getY() / distanceTravel ) + ( FastRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        motion.setZ( (float) ( ( ( motion.getZ() / distanceTravel ) + ( FastRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        this.setVelocity( motion );

        // Set owning entity
        this.metadataContainer.putLong( 5, player.getEntityId() );
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public float getDamage() {
        return 0;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        // Ender pearls which hit are gone
        if ( this.hitEntity != null ) {
            // Teleport
            this.teleportShooter();
            this.despawn();
        }

        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= Values.CLIENT_TICK_RATE ) {
            if ( this.isCollided ) {
                Set<Block> blocks = new HashSet<>( this.collidedWith );
                ProjectileHitBlocksEvent hitBlocksEvent = new ProjectileHitBlocksEvent( blocks, this );
                this.world.getServer().getPluginManager().callEvent( hitBlocksEvent );
                if ( !hitBlocksEvent.isCancelled() ) {
                    // Teleport
                    this.teleportShooter();
                    this.despawn();
                }
            }

            // Despawn after 1200 ticks ( 1 minute )
            if ( this.age >= 1200 ) {
                this.despawn();
            }
        }
    }

    private void teleportShooter() {
        this.shooter.attack( 5.0f, EntityDamageEvent.DamageSource.FALL );
        this.shooter.teleport( this.getLocation(), EntityTeleportEvent.Cause.ENDERPEARL );
    }

}
