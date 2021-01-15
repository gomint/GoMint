package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockStationaryWater;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:water" )
public class StationaryWater extends Liquid<BlockStationaryWater> implements BlockStationaryWater {

    @Override
    public long breakTime() {
        return 150000;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float fillHeight() {
        return 1f;
    }

    @Override
    public int getTickDiff() {
        return 250;
    }

    @Override
    public boolean isFlowing() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 500.0f;
    }

    @Override
    public void onEntityStanding(EntityLiving<?> entityLiving ) {
        if ( entityLiving.burning() ) {
            entityLiving.extinguish();
        }
    }

    @Override
    public BlockType blockType() {
        return BlockType.STATIONARY_WATER;
    }

}
