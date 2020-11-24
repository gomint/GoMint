/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.AttributeModifier;
import io.gomint.server.entity.AttributeModifierType;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 18 )
public class Weakness extends Effect {

    @Override
    public byte getId() {
        return 18;
    }

    @Override
    public void apply( EntityLiving entity ) {
        entity.getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .setModifier( AttributeModifier.WEAKNESS_EFFECT, AttributeModifierType.MULTIPLY, -( 0.5f * ( this.amplifier + 1 ) ) );
    }

    @Override
    public void update( long currentTimeMillis, float dT ) {

    }

    @Override
    public void remove( EntityLiving entity ) {
        entity.getAttributeInstance( Attribute.ATTACK_DAMAGE )
            .removeModifier( AttributeModifier.WEAKNESS_EFFECT );
    }

}
