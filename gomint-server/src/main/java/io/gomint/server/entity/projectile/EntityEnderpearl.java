/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ender_pearl" )
public class EntityEnderpearl extends EntityThrowable implements io.gomint.entity.projectile.EntityEnderpearl {

    private float lastUpdateDT;

    /**
     * Create entity for API
     */
    public EntityEnderpearl() {
        super( null, EntityType.THROWN_ENDERPEARL, null );
    }

    /**
     * Construct a new Entity
     *
     * @param player which spawned this hook
     * @param world  The world in which this entity is in
     */
    public EntityEnderpearl( EntityPlayer player, WorldAdapter world ) {
        super( player, EntityType.THROWN_ENDERPEARL, world );

        // Calculate starting position
        Location position = this.setPositionFromShooter();

        // Calculate motion
        this.setMotionFromEntity(position, this.shooter.getVelocity(), 0f, 1.5f, 1f);

        // Calculate correct yaw / pitch
        this.setLookFromMotion();
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

        this.lastUpdateDT += dT;
        if ( Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON ) {
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

            this.lastUpdateDT = 0;
        }
    }

    private void teleportShooter() {
        this.shooter.attack( 5.0f, EntityDamageEvent.DamageSource.FALL );
        this.shooter.teleport( this.getLocation(), EntityTeleportEvent.Cause.ENDERPEARL );
    }

}
