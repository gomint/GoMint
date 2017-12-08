/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.util.random.FastRandom;
import io.gomint.server.world.LevelEvent;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Particle;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 68 )
public class EntityExpBottle extends EntityThrowable implements io.gomint.entity.projectile.EntityExpBottle {

    private float lastUpdatedT;

    /**
     * Construct a new Entity
     *
     * @param shooter of this entity
     * @param world   The world in which this entity is in
     */
    public EntityExpBottle( EntityLiving shooter, WorldAdapter world ) {
        super( shooter, EntityType.EXP_BOTTLE_PROJECTILE, world );

        // Gravity
        GRAVITY = 0.1f;
        DRAG = 0.01f;
    }

    /**
     * Create entity for API
     */
    public EntityExpBottle() {
        super( null, EntityType.EXP_BOTTLE_PROJECTILE, null );
        this.setSize( 0.25f, 0.25f );

        // Gravity
        GRAVITY = 0.1f;
        DRAG = 0.01f;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        if ( this.hitEntity != null ) {
            this.breakBottle();
            this.despawn();
        }

        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= Values.CLIENT_TICK_RATE ) {
            if ( this.isCollided ) {
                this.breakBottle();
                this.despawn();
            }

            // Despawn after 1200 ticks ( 1 minute )
            if ( this.age >= 1200 ) {
                this.despawn();
            }
        }
    }

    private void breakBottle() {
        Location location = this.getLocation();
        this.world.sendParticle( location, Particle.MOB_SPELL );
        this.world.sendLevelEvent( location, LevelEvent.PARTICLE_SPLASH, 0x00385dc6 );

        int amountOfOrbs = 3 + FastRandom.current().nextInt( 8 );
        int add = 1;
        for ( int i = 0; i < amountOfOrbs; i += add ) {
            this.world.createExpOrb( location, add );
            add = 1 + FastRandom.current().nextInt( 2 );
        }
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        return ( damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.VOID ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.ON_FIRE ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.ENTITY_EXPLODE )
            && super.damage( damageEvent );
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
