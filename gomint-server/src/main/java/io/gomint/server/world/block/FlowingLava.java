package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFlowingLava;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 10 )
public class FlowingLava extends Liquid implements BlockFlowingLava {

    @Override
    public int getBlockId() {
        return 10;
    }

    @Override
    public long getBreakTime() {
        return 150000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void onEntityStanding( EntityLiving entityLiving ) {
        EntityDamageEvent damageEvent = new EntityDamageEvent( entityLiving, EntityDamageEvent.DamageSource.LAVA, 4.0f );
        entityLiving.damage( damageEvent );
        entityLiving.setFire( 15 );
        entityLiving.multiplyFallDistance( 0.5f );
    }

    @Override
    public float getBlastResistance() {
        return 500.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.FLOWING_LAVA;
    }

}