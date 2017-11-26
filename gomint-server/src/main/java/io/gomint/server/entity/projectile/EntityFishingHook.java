/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.util.Values;
import io.gomint.server.util.random.FastRandom;
import io.gomint.server.world.WorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityFishingHook extends EntityProjectile implements io.gomint.entity.projectile.EntityFishingHook {

    private static final Vector WATER_FLOATING_MOTION = new Vector( 0, 0.1f, 0 );

    private float lastUpdatedT;

    /**
     * Construct a new Entity
     *
     * @param player which spawned this hook
     * @param world  The world in which this entity is in
     */
    public EntityFishingHook( EntityPlayer player, WorldAdapter world ) {
        super( player, EntityType.FISHING_HOOK, world );
        this.setSize( 0.25f, 0.25f );

        // Calculate starting position
        Location position = player.getLocation();
        position.add(
            -(float) ( Math.cos( position.getYaw() / 180 * Math.PI ) * 0.16f ),
            player.getEyeHeight() - 0.1f,
            -(float) ( Math.sin( position.getYaw() / 180 * Math.PI ) * 0.16f )
        );

        this.setPosition( position );
        this.setYaw( position.getYaw() );
        this.setPitch( position.getPitch() );

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

        // Set owning entity (this draws the rod line)
        this.metadataContainer.putLong( 5, player.getEntityId() );
    }

    /**
     * Retract the hook to the origin
     *
     * @return damage which should be dealt to the item stack
     */
    public int retract() {
        this.despawn();
        return 2;
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

        if ( this.shooter.isDead() ) {
            this.despawn();
        }

        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= Values.CLIENT_TICK_RATE ) {
            if ( this.isCollided && this.isInsideLiquid() ) {
                if ( !this.getVelocity().equals( WATER_FLOATING_MOTION ) ) {
                    this.setVelocity( WATER_FLOATING_MOTION );
                }
            } else if ( this.isCollided ) {
                if ( !this.getVelocity().equals( Vector.ZERO ) ) {
                    this.setVelocity( Vector.ZERO );
                }
            }
        }
    }

}
