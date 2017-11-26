/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.event.entity.EntityHealEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 10 )
public class Regeneration extends Effect {

    private EntityLiving player;
    private float addHealthEvery;
    private float lastUpdatedT;

    public Regeneration( int amplifier, long runoutTimer ) {
        super( amplifier, runoutTimer );
        this.addHealthEvery = ( 50 >> amplifier ) / 20f;
    }

    @Override
    public void apply( EntityLiving player ) {
        this.player = player;
    }

    @Override
    public void update( long currentTimeMillis, float dT ) {
        this.lastUpdatedT += dT;
        if ( this.lastUpdatedT >= this.addHealthEvery ) {
            this.player.heal( 1f, EntityHealEvent.Cause.REGENERATION_EFFECT );
            this.lastUpdatedT = 0;
        }
    }

    @Override
    public void remove( EntityLiving player ) {
        this.player = null;
    }

}
