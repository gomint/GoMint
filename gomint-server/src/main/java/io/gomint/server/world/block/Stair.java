package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.CrossDirectionBlockState;
import io.gomint.world.block.BlockDirection;
import io.gomint.world.block.BlockFacing;
import io.gomint.world.block.BlockStair;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Stair extends Block implements BlockStair {

    private final CrossDirectionBlockState direction = new CrossDirectionBlockState( this, () -> "weirdo_direction" );
    private final BooleanBlockState top = new BooleanBlockState( this, () -> "upside_down_bit" );

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        // TODO: Fix bounding box when top / directional
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + .5f,
            this.location.getZ() + 1
        ) );
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction.setState(direction);
    }

    @Override
    public Direction getDirection() {
        return this.direction.getState();
    }

    @Override
    public boolean isTop() {
        return this.top.getState();
    }

    @Override
    public void setTop(boolean top) {
        this.top.setState(top);
    }

}
