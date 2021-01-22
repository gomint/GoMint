/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.player.EffectManager;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Effect implements io.gomint.entity.potion.Effect {

    private EffectManager manager;
    protected int amplifier;
    private long runoutTimer;
    protected boolean visible = true;

    public int getAmplifier() {
        return amplifier;
    }

    public long getRunoutTimer() {
        return runoutTimer;
    }

    public boolean isVisible() {
        return visible;
    }

    public abstract byte getId();

    public abstract void apply( EntityLiving<?> entity );

    public abstract void update( long currentTimeMillis, float dT );

    public abstract void remove( EntityLiving<?> entity );

    @Override
    public Effect visible(boolean value ) {
        this.visible = value;
        this.manager.updateEffect( this );
        return this;
    }

    public void setData( EffectManager manager, int amplifier, long lengthInMS ) {
        this.manager = manager;
        this.amplifier = amplifier;
        this.runoutTimer = lengthInMS;
    }

}
