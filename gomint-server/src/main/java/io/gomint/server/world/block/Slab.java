package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Slab extends Block {

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        if ( ( getBlockData() & 0x08 ) > 0 ) {
            return Collections.singletonList( new AxisAlignedBB(
                this.location.getX(),
                this.location.getY() + 0.5f,
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 1,
                this.location.getZ() + 1
            ) );
        } else {
            return Collections.singletonList( new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 0.5f,
                this.location.getZ() + 1
            ) );
        }
    }

}
