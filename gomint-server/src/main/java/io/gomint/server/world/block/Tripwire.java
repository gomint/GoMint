package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 132 )
public class Tripwire extends Block implements io.gomint.world.block.BlockTripwire {

    @Override
    public int getBlockId() {
        return 132;
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
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public BlockType getType() {
        return BlockType.TRIPWIRE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 0.15625f,
            this.location.getZ() + 1
        ) );
    }

}
