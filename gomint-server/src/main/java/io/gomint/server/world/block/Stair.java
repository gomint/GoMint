package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
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
public abstract class Stair<B> extends Block implements BlockStair<B> {

    private static final CrossDirectionBlockState DIRECTION = new CrossDirectionBlockState(() -> new String[]{"weirdo_direction"});
    private static final BooleanBlockState TOP = new BooleanBlockState(() -> new String[]{"upside_down_bit"});

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        // TODO: Fix bounding box when top / directional
        return Collections.singletonList(new AxisAlignedBB(
            this.location.x(),
            this.location.y(),
            this.location.z(),
            this.location.x() + 1,
            this.location.y() + .5f,
            this.location.z() + 1
        ));
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location, Vector clickVector) {
        DIRECTION.detectFromPlacement(this, entity, item, face, clickVector);

        TOP.state(this, face == Facing.DOWN);

        return super.beforePlacement(entity, item, face, location, clickVector);
    }

    @Override
    public B direction(Direction direction) {
        DIRECTION.state(this, direction);
        return (B) this;
    }

    @Override
    public Direction direction() {
        return DIRECTION.state(this);
    }

    @Override
    public boolean top() {
        return TOP.state(this);
    }

    @Override
    public B top(boolean top) {
        TOP.state(this, top);
        return (B) this;
    }

}
