package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 171 )
public class Carpet extends Block implements io.gomint.world.block.BlockCarpet {

    @Override
    public int getBlockId() {
        return 171;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 0.0625f,
            this.location.getZ() + 1
        );
    }
    @Override
    public float getBlastResistance() {
        return 0.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CARPET;
    }

}