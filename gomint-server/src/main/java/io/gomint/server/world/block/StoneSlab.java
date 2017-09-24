package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 44 )
public class StoneSlab extends Block {

    @Override
    public int getBlockId() {
        return 44;
    }

    @Override
    public long getBreakTime() {
        return 3000;
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
                this.location.getY() + 0.5f,
                this.location.getZ() + 1
        );
    }

}
