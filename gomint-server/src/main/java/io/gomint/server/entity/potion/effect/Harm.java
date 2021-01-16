/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.player.EffectManager;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 7 )
public class Harm extends Effect {

    public Harm() {
        this.visible = false;
    }

    @Override
    public byte getId() {
        return 7;
    }

    @Override
    public void apply( EntityLiving<?> entity ) {
        // TODO: Effect for undead
        entity.attack( 6 << this.amplifier, EntityDamageEvent.DamageSource.HARM_EFFECT );
    }

    @Override
    public void update( long currentTimeMillis, float dT ) {

    }

    @Override
    public void remove( EntityLiving<?> entity ) {

    }

}
