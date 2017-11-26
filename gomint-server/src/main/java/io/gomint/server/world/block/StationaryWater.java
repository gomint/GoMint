package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockStationaryWater;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 9 )
public class StationaryWater extends Liquid implements BlockStationaryWater {

    @Override
    public int getBlockId() {
        return 9;
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
    public float getFillHeight() {
        return 1f;
    }

    @Override
    public float getBlastResistance() {
        return 500.0f;
    }

    @Override
    public void onEntityStanding( EntityLiving entityLiving ) {
        if ( entityLiving.isOnFire() ) {
            entityLiving.setFire( 0 );
        }
    }

    @Override
    public BlockType getType() {
        return BlockType.STATIONARY_WATER;
    }

}