package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.HalfBlockState;
import io.gomint.world.block.BlockSlab;
import io.gomint.world.block.data.Facing;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Slab<B> extends Block implements BlockSlab<B> {

    protected static final BooleanBlockState TOP = new HalfBlockState( () -> new String[]{"top_slot_bit"} );

    @Override
    public B top(boolean top ) {
        TOP.state( this, top );
        return (B) this;
    }

    @Override
    public boolean top() {
        return TOP.state( this );
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location, Vector clickVector) {
        TOP.detectFromPlacement(this, entity, item, face, clickVector);
        return true;
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        if ( this.top() ) {
            return Collections.singletonList( new AxisAlignedBB(
                this.location.x(),
                this.location.y() + 0.5f,
                this.location.z(),
                this.location.x() + 1,
                this.location.y() + 1,
                this.location.z() + 1
            ) );
        } else {
            return Collections.singletonList( new AxisAlignedBB(
                this.location.x(),
                this.location.y(),
                this.location.z(),
                this.location.x() + 1,
                this.location.y() + 0.5f,
                this.location.z() + 1
            ) );
        }
    }

}
