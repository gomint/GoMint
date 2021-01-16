/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.event.entity.EntityHealEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.player.EffectManager;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 6 )
public class Healing extends Effect {

    public Healing() {
        this.visible = false;
    }

    @Override
    public byte getId() {
        return 6;
    }

    @Override
    public void apply( EntityLiving<?> player ) {
        // TODO: Implement undead effect
        player.heal( 4 << this.amplifier, EntityHealEvent.Cause.HEALING_EFFECT );
    }

    @Override
    public void update( long currentTimeMillis, float dT ) {

    }

    @Override
    public void remove( EntityLiving<?> player ) {

    }

}
