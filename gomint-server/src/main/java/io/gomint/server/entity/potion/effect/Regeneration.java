/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.effect;

import io.gomint.event.entity.EntityHealEvent;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.player.EffectManager;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 10)
public class Regeneration extends Effect {

    private EntityLiving<?> player;
    private float addHealthEvery;
    private float lastUpdatedT;

    @Override
    public byte getId() {
        return 10;
    }

    @Override
    public void apply(EntityLiving<?> player) {
        this.player = player;
    }

    @Override
    public void update(long currentTimeMillis, float dT) {
        this.lastUpdatedT += dT;
        if (this.addHealthEvery - this.lastUpdatedT < MathUtils.EPSILON) {
            this.player.heal(1f, EntityHealEvent.Cause.REGENERATION_EFFECT);
            this.lastUpdatedT = 0;
        }
    }

    @Override
    public void remove(EntityLiving<?> player) {
        this.player = null;
    }

    @Override
    public void setData(EffectManager manager, int amplifier, long lengthInMS) {
        super.setData(manager, amplifier, lengthInMS);
        this.addHealthEvery = (((int) Values.CLIENT_TICK_MS) >> amplifier) / 20f;
    }

}
