/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.inventory.item.ItemArrow;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.util.Values;
import io.gomint.server.util.random.FastRandom;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityArrow extends EntityProjectile implements io.gomint.entity.projectile.EntityArrow {

    private boolean canBePickedup;
    private boolean critical;
    private float lastUpdatedT;

    /**
     * Construct a new Entity
     *
     * @param player which spawned this hook
     * @param world  The world in which this entity is in
     * @param force  with which the player shoot the bow
     */
    public EntityArrow( EntityPlayer player, WorldAdapter world, float force ) {
        super( player, EntityType.ARROW, world );
        this.setSize( 0.5f, 0.5f );

        this.critical = force == 1.0f;
        float applyForce = force * 2;

        Location position = this.setPositionFromShooter();

        // Calculate motion
        Vector motion = new Vector(
            (float) ( -Math.sin( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( -Math.sin( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( Math.cos( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f )
        );

        float distanceTravel = (float) Math.sqrt( MathUtils.square( motion.getX() ) + MathUtils.square( motion.getY() ) + MathUtils.square( motion.getZ() ) );
        motion.setX( (float) ( ( ( motion.getX() / distanceTravel ) + ( FastRandom.current().nextDouble() * ( FastRandom.current().nextBoolean() ? -1 : 1 ) * 0.0075f ) ) * ( applyForce * 1.5f ) ) );
        motion.setY( (float) ( ( ( motion.getY() / distanceTravel ) + ( FastRandom.current().nextDouble() * ( FastRandom.current().nextBoolean() ? -1 : 1 ) * 0.0075f ) ) * ( applyForce * 1.5f ) ) );
        motion.setZ( (float) ( ( ( motion.getZ() / distanceTravel ) + ( FastRandom.current().nextDouble() * ( FastRandom.current().nextBoolean() ? -1 : 1 ) * 0.0075f ) ) * ( applyForce * 1.5f ) ) );
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
        this.metadataContainer.putLong( 5, player.getEntityId() );
    }

    @Override
    public boolean isCritical() {
        return this.critical;
    }

    @Override
    public float getDamage() {
        return 2;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        // Arrows which hit are gone
        if ( this.hitEntity != null ) {
            this.despawn();
        }

        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= Values.CLIENT_TICK_RATE ) {
            if ( this.isCollided ) {
                // You may pick this up now
                this.canBePickedup = true;
            }

            if ( this.canBePickedup ) {
                if ( !this.getVelocity().equals( Vector.ZERO ) ) {
                    this.setVelocity( Vector.ZERO );
                    this.setAffectedByGravity( false );
                }
            }

            // Despawn after 1200 ticks ( 1 minute )
            if ( this.age >= 1200 ) {
                this.despawn();
            }
        }
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        if ( this.canBePickedup && !this.isDead() ) {
            ItemArrow arrow = ItemArrow.create( 1 );

            // Check if we have place in out inventory to store this item
            if ( !player.getInventory().hasPlaceFor( arrow ) ) {
                return;
            }

            player.getInventory().addItem( arrow );
            this.despawn();
        }
    }

}
