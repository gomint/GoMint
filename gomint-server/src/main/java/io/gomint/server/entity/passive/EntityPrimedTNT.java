/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.passive;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityFlag;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.Explosion;
import io.gomint.server.world.LevelEvent;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:tnt" )
public class EntityPrimedTNT extends Entity<io.gomint.entity.active.EntityPrimedTNT> implements io.gomint.entity.active.EntityPrimedTNT {

    private float lastUpdateDT;
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
        this.position( position );
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
        this.size( 0.98f, 0.98f );

        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.IGNITED, true );
        this.metadataContainer.putInt( MetadataContainer.DATA_FUSE_LENGTH, this.fuse );

        this.offsetY = 0.49f;
    }

    @Override
    public EntityPrimedTNT fuse(float fuseInSeconds ) {
        this.fuse = (int) ( fuseInSeconds * 20f );
        return this;
    }

    @Override
    public float fuse() {
        return this.fuse / 20f;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        this.lastUpdateDT += dT;
        if ( Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON ) {
            // Update client
            if ( this.fuse % 5 == 0 ) {
                this.metadataContainer.putInt( MetadataContainer.DATA_FUSE_LENGTH, this.fuse );
            }

            if ( this.onGround ) {
                Vector motion = this.velocity();
                this.velocity( motion.multiply( new Vector( 0.7f, -0.5f, 0.7f ) ) );
            }

            this.fuse--;
            if ( this.fuse <= 0 ) {
                new Explosion( 4, this ).explode( currentTimeMS, dT );
                despawn();
            }

            this.lastUpdateDT = 0;
        }
    }

    @Override
    protected EntityPrimedTNT fall() {
        return this;
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        if ( damageEvent.damageSource() == EntityDamageEvent.DamageSource.VOID && super.damage( damageEvent ) ) {
            this.despawn();
            return true;
        }

        return false;
    }

    @Override
    public EntityPrimedTNT spawn( Location location ) {
        super.spawn( location );

        this.world.sendLevelEvent( location, LevelEvent.SOUND_IGNITE, 0 );
        return this;
    }

    @Override
    public Set<String> tags() {
        return EntityTags.PASSIVE;
    }

}
