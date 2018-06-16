package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockStationaryLava;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 11 )
public class StationaryLava extends Liquid implements BlockStationaryLava {

    @Override
    public int getBlockId() {
        return 11;
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
    public float getFillHeight() {
        return 1f;
    }

    @Override
    public int getTickDiff() {
        return 1500; // Depends on the world, in nether its 10 ticks / otherwise its 30
    }

    @Override
    public boolean isFlowing() {
        return false;
    }

    @Override
    public void onEntityStanding( EntityLiving entityLiving ) {
        entityLiving.attack( 4.0f, EntityDamageEvent.DamageSource.LAVA );
        entityLiving.setBurning( 15, TimeUnit.SECONDS );
        entityLiving.multiplyFallDistance( 0.5f );
    }

    @Override
    protected byte getFlowDecayPerBlock() {
        return 2;
    }

    @Override
    public float getBlastResistance() {
        return 500.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STATIONARY_LAVA;
    }

}
