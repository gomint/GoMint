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
public abstract class Door<B extends BlockDoor<B>> extends Block implements BlockDoor<B> {

    private static final BooleanBlockState HINGE = new BooleanBlockState(() -> new String[]{"door_hinge_bit"});
    private static final BooleanBlockState TOP = new BooleanBlockState(() -> new String[]{"upper_block_bit"});
    private static final BooleanBlockState OPEN = new BooleanBlockState(() -> new String[]{"open_bit"});
    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"}); // Rotation is always clockwise

    @Override
    public boolean top() {
        return TOP.getState(this);
    }

    protected B top(boolean top) {
        TOP.setState(this, top);
        return (B) this;
    }

    @Override
    public boolean open() {
        return OPEN.getState(this);
    }

    @Override
    public B toggle() {
        boolean toApply = !this.open();

        if (top()) {
            B otherPart = this.world.blockAt(this.position.add(BlockPosition.DOWN));
            otherPart.open(toApply);
        } else {
            B otherPart = this.world.blockAt(this.position.add(BlockPosition.UP));
            otherPart.open(toApply);
        }

        this.open(toApply);
        return (B) this;
    }

    @Override
    public boolean interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        // Open / Close the door
        // TODO: Door events
        toggle();

        return true;
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        Block above = this.world.blockAt(this.position.add(BlockPosition.UP));
        if (above.canBeReplaced(item)) {
            DIRECTION.detectFromPlacement(this, entity, item, face);
            TOP.setState(this, false);
            OPEN.setState(this, false);
            return true;
        }

        return false;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public long breakTime() {
        return 4500;
    }

    @Override
    public boolean onBreak(boolean creative) {
        if (top()) {
            Block otherPart = this.world.blockAt(this.position.add(BlockPosition.DOWN));
            otherPart.blockType(BlockAir.class);
        } else {
            Block otherPart = this.world.blockAt(this.position.add(BlockPosition.UP));
            otherPart.blockType(BlockAir.class);
        }

        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public B direction(Direction direction) {
        DIRECTION.setState(this, direction);
        return (B) this;
    }

    @Override
    public Direction direction() {
        return DIRECTION.getState(this);
    }

    @Override
    public B hingeSide(HingeSide side) {
        HINGE.setState(this, side == HingeSide.RIGHT);
        return (B) this;
    }

    @Override
    public HingeSide hingeSide() {
        return HINGE.getState(this) ? HingeSide.RIGHT : HingeSide.LEFT;
    }

    @Override
    public B open(boolean open) {
        OPEN.setState(this, open);
        return (B) this;
    }

}
