package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.inventory.item.ItemHeavyWeightedPressurePlate;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 148 )
public class HeavyWeightedPressurePlate extends Block implements io.gomint.world.block.BlockHeavyWeightedPressurePlate {

    @Override
    public int getBlockId() {
        return 148;
    }

    @Override
    public long getBreakTime() {
        return 750;
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
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 0.1f,
            this.location.getZ() + 1
        );
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.HEAVY_WEIGHTED_PRESSURE_PLATE;
    }

}