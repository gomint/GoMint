package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFlowingWater;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 8 )
public class FlowingWater extends Liquid implements BlockFlowingWater {

    @Override
    public int getBlockId() {
        return 8;
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
        return BlockType.FLOWING_WATER;
    }

}