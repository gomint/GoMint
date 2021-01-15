/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.player.EffectManager;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 13 )
public class WaterBreathing extends Effect {

    @Override
    public byte getId() {
        return 13;
    }

    @Override
    public void apply( EntityLiving<?> entity ) {

    }

    @Override
    public void update( long currentTimeMillis, float dT ) {

    }

    @Override
    public void remove( EntityLiving<?> entity ) {

    }

}
