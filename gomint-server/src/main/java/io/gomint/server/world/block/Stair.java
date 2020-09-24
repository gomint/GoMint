package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.CrossDirectionBlockState;
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

    private static final CrossDirectionBlockState DIRECTION = new CrossDirectionBlockState(() -> new String[]{"weirdo_direction"});
    private static final BooleanBlockState TOP = new BooleanBlockState(() -> new String[]{"upside_down_bit"});

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        // TODO: Fix bounding box when top / directional
        return Collections.singletonList(new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + .5f,
            this.location.getZ() + 1
        ));
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        DIRECTION.detectFromPlacement(this, entity, item, face);

        TOP.setState(this, face == Facing.DOWN);

        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    public void setDirection(Direction direction) {
        DIRECTION.setState(this, direction);
    }

    @Override
    public Direction getDirection() {
        return DIRECTION.getState(this);
    }

    @Override
    public boolean isTop() {
        return TOP.getState(this);
    }

    @Override
    public void setTop(boolean top) {
        TOP.setState(this, top);
    }

}
