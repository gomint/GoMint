/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 7 )
public class Harm extends Effect {

    public Harm( int amplifier, long runoutTimer ) {
        super( amplifier, runoutTimer );
        this.visible = false;
    }

    @Override
    public void apply( EntityLiving entity ) {
        // TODO: Effect for undead
        EntityDamageEvent damageEvent = new EntityDamageEvent( entity, EntityDamageEvent.DamageSource.HARM_EFFECT, 6 << this.amplifier );
        entity.damage( damageEvent );
    }

    @Override
    public void update( long currentTimeMillis, float dT ) {

    }

    @Override
    public void remove( EntityLiving entity ) {

    }

}
