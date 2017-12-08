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
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.util.random.FastRandom;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class EntityThrowable extends EntityProjectile {

    /**
     * Construct a new Entity
     *
     * @param shooter of this entity
     * @param type    The type of the Entity
     * @param world   The world in which this entity is in
     */
    protected EntityThrowable( EntityLiving shooter, EntityType type, WorldAdapter world ) {
        super( shooter, type, world );
        this.setSize( 0.25f, 0.25f );

        Location position = this.setPositionFromShooter();

        // Calculate motion
        Vector motion = new Vector(
            (float) ( -Math.sin( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( -Math.sin( ( position.getPitch() - 20f ) / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( Math.cos( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f )
        );

        float distanceTravel = (float) Math.sqrt( MathUtils.square( motion.getX() ) + MathUtils.square( motion.getY() ) + MathUtils.square( motion.getZ() ) );
        motion.setX( (float) ( ( ( motion.getX() / distanceTravel ) + ( FastRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        motion.setY( (float) ( ( ( motion.getY() / distanceTravel ) + ( FastRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        motion.setZ( (float) ( ( ( motion.getZ() / distanceTravel ) + ( FastRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        this.setVelocity( motion );

        // Calculate correct yaw / pitch
        double motionDistance = MathUtils.square( motion.getX() ) + MathUtils.square( motion.getZ() );
        float motionForce = (float) Math.sqrt( motionDistance );

        float yaw = (float) ( Math.atan2( motion.getX(), motion.getZ() ) * 180.0D / Math.PI );
        float pitch = (float) ( Math.atan2( motion.getY(), (double) motionForce ) * 180.0D / Math.PI );

        this.setYaw( yaw );
        this.setHeadYaw( yaw );
        this.setPitch( pitch );

        // Set owning entity
        this.metadataContainer.putLong( 5, shooter.getEntityId() );
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public float getDamage() {
        return 0;
    }

}
