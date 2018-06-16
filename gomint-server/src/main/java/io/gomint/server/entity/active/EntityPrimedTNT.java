/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.active;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityFlag;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.Explosion;
import io.gomint.server.world.LevelEvent;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 65 )
public class EntityPrimedTNT extends Entity implements io.gomint.entity.active.EntityPrimedTNT {

    private float lastUpdatedT;
    private int fuse;

    /**
     * Construct a new Entity
     *
     * @param world               The world in which this entity is in
     * @param position            of the entity
     * @param ticksUntilExplosion how many ticks (client ticks) until this tnt explodes
     */
    public EntityPrimedTNT( WorldAdapter world, Vector position, int ticksUntilExplosion ) {
        super( EntityType.PRIMED_TNT, world );

        this.fuse = ticksUntilExplosion;
        this.initEntity();

        this.world.sendLevelEvent( position, LevelEvent.SOUND_IGNITE, 0 );
        this.setPosition( position );
    }

    /**
     * Constructor for the API. Only for external use
     */
    public EntityPrimedTNT() {
        super( EntityType.PRIMED_TNT, null );

        this.fuse = 80;
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.98f, 0.98f );

        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.IGNITED, true );
        this.metadataContainer.putInt( MetadataContainer.DATA_FUSE_LENGTH, this.fuse );

        this.offsetY = 0.49f;
    }

    @Override
    public void setFuse( float fuseInSeconds ) {
        this.fuse = (int) ( fuseInSeconds * 20f );
    }

    @Override
    public float getFuse() {
        return this.fuse / 20f;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= Values.CLIENT_TICK_RATE ) {
            // Update client
            if ( this.fuse % 5 == 0 ) {
                this.metadataContainer.putInt( MetadataContainer.DATA_FUSE_LENGTH, this.fuse );
            }

            if ( this.onGround ) {
                Vector motion = this.getVelocity();
                motion.multiply( new Vector( 0.7f, -0.5f, 0.7f ) );
                this.setVelocity( motion );
            }

            this.fuse--;
            if ( this.fuse <= 0 ) {
                new Explosion( 4, this ).explode( currentTimeMS, dT );
                despawn();
            }

            this.lastUpdatedT = 0;
        }
    }

    @Override
    protected void fall() {

    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        if ( damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.VOID && super.damage( damageEvent ) ) {
            this.despawn();
            return true;
        }

        return false;
    }

    @Override
    public void spawn( Location location ) {
        super.spawn( location );

        this.world.sendLevelEvent( location, LevelEvent.SOUND_IGNITE, 0 );
    }

}
