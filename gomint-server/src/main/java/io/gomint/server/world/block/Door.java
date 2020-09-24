package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.BlockDoor;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.HingeSide;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Door extends Block implements BlockDoor {

    private static final BooleanBlockState HINGE = new BooleanBlockState(() -> new String[]{"door_hinge_bit"});
    private static final BooleanBlockState TOP = new BooleanBlockState(() -> new String[]{"upper_block_bit"});
    private static final BooleanBlockState OPEN = new BooleanBlockState(() -> new String[]{"open_bit"});
    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"}); // Rotation is always clockwise

    @Override
    public boolean isTop() {
        return TOP.getState(this);
    }

    protected void setTop(boolean top) {
        TOP.setState(this, top);
    }

    @Override
    public boolean isOpen() {
        return OPEN.getState(this);
    }

    @Override
    public void toggle() {
        boolean toApply = !this.isOpen();

        if (isTop()) {
            Door otherPart = this.world.getBlockAt(this.position.add(BlockPosition.DOWN));
            otherPart.setOpen(toApply);
        } else {
            Door otherPart = this.world.getBlockAt(this.position.add(BlockPosition.UP));
            otherPart.setOpen(toApply);
        }

        this.setOpen(toApply);
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        // Open / Close the door
        // TODO: Door events
        toggle();

        return true;
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        Block above = this.world.getBlockAt(this.position.add(BlockPosition.UP));
        if (above.canBeReplaced(item)) {
            DIRECTION.detectFromPlacement(this, entity, item, face);
            TOP.setState(this, false);
            OPEN.setState(this, false);
            return true;
        }

        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean onBreak(boolean creative) {
        if (isTop()) {
            Block otherPart = this.world.getBlockAt(this.position.add(BlockPosition.DOWN));
            otherPart.setBlockType(BlockAir.class);
        } else {
            Block otherPart = this.world.getBlockAt(this.position.add(BlockPosition.UP));
            otherPart.setBlockType(BlockAir.class);
        }

        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
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
    public void setHingeSide(HingeSide side) {
        HINGE.setState(this, side == HingeSide.RIGHT);
    }

    @Override
    public HingeSide getHingeSide() {
        return HINGE.getState(this) ? HingeSide.RIGHT : HingeSide.LEFT;
    }

    protected void setOpen(boolean open) {
        OPEN.setState(this, open);
    }

}
