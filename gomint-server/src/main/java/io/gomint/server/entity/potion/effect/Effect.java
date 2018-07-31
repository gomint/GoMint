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
public abstract class Effect implements io.gomint.entity.potion.Effect {

    private EffectManager manager;
    @Getter protected int amplifier;
    @Getter private long runoutTimer;
    @Getter protected boolean visible = true;

    public abstract byte getId();
    public abstract void apply( EntityLiving entity );
    public abstract void update( long currentTimeMillis, float dT );
    public abstract void remove( EntityLiving entity );

    public void setVisible( boolean value ) {
        this.visible = value;
        this.manager.updateEffect( this );
    }

    public void setData( EffectManager manager, int amplifier, long lengthInMS ) {
        this.manager = manager;
        this.amplifier = amplifier;
        this.runoutTimer = lengthInMS;
    }

}
