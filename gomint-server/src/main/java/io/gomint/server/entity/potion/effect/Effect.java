/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.player.EffectManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Effect implements io.gomint.entity.potion.Effect {

    private final EffectManager manager;
    @Getter protected final int amplifier;
    @Getter private final long runoutTimer;
    @Getter protected boolean visible = true;

    public abstract byte getId();
    public abstract void apply( EntityLiving entity );
    public abstract void update( long currentTimeMillis, float dT );
    public abstract void remove( EntityLiving entity );

    public void setVisible( boolean value ) {
        this.visible = value;
        this.manager.updateEffect( this );
    }

}
