/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 2 )
public class Slowness extends Effect {

    public Slowness( int amplifier, long runoutTimer ) {
        super( amplifier, runoutTimer );
    }

    @Override
    public void apply( EntityLiving player ) {
        player.getAttributeInstance( Attribute.MOVEMENT_SPEED ).setModifier( AttributeModifier.SLOWNESS_EFFECT, -( ( this.amplifier + 1 ) * 0.15f ) );
    }

    @Override
    public void update( long currentTimeMillis, float dT ) {

    }

    @Override
    public void remove( EntityLiving player ) {
        player.getAttributeInstance( Attribute.MOVEMENT_SPEED ).removeModifier( AttributeModifier.SLOWNESS_EFFECT );
    }

}
