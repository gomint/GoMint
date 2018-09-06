package io.gomint.server.entity.potion.effect;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;

@RegisterInfo( id = 16 )
public class NightVision extends Effect {

    @Override
    public byte getId() {
        return 16;
    }

    @Override
    public void apply(EntityLiving entity) {

    }

    @Override
    public void update(long currentTimeMillis, float dT) {

    }

    @Override
    public void remove(EntityLiving entity) {

    }
}
