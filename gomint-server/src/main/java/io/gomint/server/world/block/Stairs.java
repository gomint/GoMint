package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Stairs extends Block {

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
                this.location.getY() + .5f,
                this.location.getZ() + 1
        );
    }

}
