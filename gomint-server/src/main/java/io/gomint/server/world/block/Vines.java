package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemVines;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.state.AttachingBlockState;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockVines;
import io.gomint.world.block.data.Facing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:vine")
public class Vines extends Block implements BlockVines {

    // Bounding boxes
    protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0f, 0.9375f, 0.0f, 1.0f, 1.0f, 1.0f);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 0.0625f, 1.0f, 1.0f);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.9375f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0625f);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0f, 0.0f, 0.9375f, 1.0f, 1.0f, 1.0f);

    // State
    private static final String[] DIRECTION_KEY = new String[]{"vine_direction_bits"};
    private static final AttachingBlockState ATTACHED_SIDES = new AttachingBlockState(() -> DIRECTION_KEY);

    @Override
    public long breakTime() {
        return 300;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void stepOn(Entity<?> entity) {
        // Reset fall distance
        entity.resetFallDistance();
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        if (ATTACHED_SIDES.getState(this) == 0) {
            return Collections.singletonList(UP_AABB);
        }

        List<AxisAlignedBB> boundingBoxes = new ArrayList<>();
        if (ATTACHED_SIDES.enabled(this, Facing.NORTH)) {
            boundingBoxes.add(NORTH_AABB);
        }

        if (ATTACHED_SIDES.enabled(this, Facing.EAST)) {
            boundingBoxes.add(EAST_AABB);
        }

        if (ATTACHED_SIDES.enabled(this, Facing.SOUTH)) {
            boundingBoxes.add(SOUTH_AABB);
        }

        if (ATTACHED_SIDES.enabled(this, Facing.WEST)) {
            boundingBoxes.add(WEST_AABB);
        }

        return boundingBoxes;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.VINES;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        if (isCorrectTool(itemInHand)) {
            return new ArrayList<>() {{
                add(ItemVines.create(1));
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        boolean ok = face != Facing.UP && face != Facing.DOWN;
        if (ok) {
            ATTACHED_SIDES.detectFromPlacement(this, entity, item, face);
        }

        return ok;
    }

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        if (updateReason == UpdateReason.RANDOM) {
            if (ThreadLocalRandom.current().nextInt(4) == 0) {
                Facing face = Facing.getRandom();

                Block other = this.side(face);
                if (face == Facing.UP && this.position.y() < 255 && other.blockType() == BlockType.AIR) {
                    Set<Facing> attachTo = new HashSet<>();
                    for (Facing facing : Facing.HORIZONTAL) {
                        if (ThreadLocalRandom.current().nextBoolean() && this.canSpreadInDirection(other, facing.opposite())) {
                            attachTo.add(facing);
                        }
                    }

                    spreadIfAttachNotEmpty(attachTo, other);
                } else if (face == Facing.DOWN && this.position.y() > 1) {
                    if (other.blockType() == BlockType.AIR) {
                        Set<Facing> attachTo = new HashSet<>();
                        for (Facing facing : Facing.HORIZONTAL) {
                            if (ThreadLocalRandom.current().nextBoolean() && ATTACHED_SIDES.enabled(this, facing)) {
                                attachTo.add(facing);
                            }
                        }

                        spreadIfAttachNotEmpty(attachTo, other);
                    } else if (other.blockType() == BlockType.VINES) {
                        Vines newVines = (Vines) other;
                        for (Facing facing : Facing.HORIZONTAL) {
                            if (ThreadLocalRandom.current().nextBoolean() && ATTACHED_SIDES.enabled(this, facing)) {
                                newVines.attach(facing);
                            }
                        }
                    }
                } else if (!ATTACHED_SIDES.enabled(this, face) && amountOfVines(9, 3, 9) < 5) {
                    if (other.blockType() == BlockType.AIR) {
                        Facing clockwiseY = face.rotateClockWiseOnY();
                        Facing counterClockwiseY = face.rotateCounterClockWiseOnY();

                        boolean attachedOnClockwise = ATTACHED_SIDES.enabled(this, clockwiseY);
                        boolean attachedOnCounterClockwise = ATTACHED_SIDES.enabled(this, counterClockwiseY);

                        Block otherClockwise = other.side(clockwiseY);
                        Block otherCounterClockwise = other.side(counterClockwiseY);

                        if (attachedOnClockwise && this.canSpreadInDirection(otherClockwise.side(clockwiseY), clockwiseY)) {
                            Vines newVines = other.blockType(Vines.class);
                            newVines.attach(clockwiseY);
                        } else if (attachedOnCounterClockwise && this.canSpreadInDirection(otherCounterClockwise.side(counterClockwiseY), counterClockwiseY)) {
                            Vines newVines = other.blockType(Vines.class);
                            newVines.attach(counterClockwiseY);
                        } else if (attachedOnClockwise && otherClockwise.blockType() == BlockType.AIR && this.canSpreadInDirection(otherClockwise, face)) {
                            Vines newVines = otherClockwise.blockType(Vines.class);
                            newVines.attach(face.opposite());
                        } else if (attachedOnCounterClockwise && otherCounterClockwise.blockType() == BlockType.AIR && this.canSpreadInDirection(otherCounterClockwise, face)) {
                            Vines newVines = otherCounterClockwise.blockType(Vines.class);
                            newVines.attach(face.opposite());
                        }
                    } else if (other.solid()) {
                        this.attach(face);
                    }
                }
            }
        }

        return -1;
    }

    private void spreadIfAttachNotEmpty(Set<Facing> attachTo, Block other) {
        if (!attachTo.isEmpty()) {
            Vines newVines = other.blockType(Vines.class);
            for (Facing value : Facing.values()) {
                newVines.detach(value);
            }

            for (Facing facing : attachTo) {
                newVines.attach(facing);
            }
        }
    }

    public void detach(Facing facing) {
        ATTACHED_SIDES.disable(this, facing);
    }

    private boolean canSpreadInDirection(Block toCheckBlock, Facing facing) {
        Block block = toCheckBlock.side(Facing.UP);
        return this.canBlockBeSpreadOn(toCheckBlock.side(facing.opposite())) &&
            (block.blockType() == BlockType.AIR || block.blockType() == BlockType.VINES || this.canBlockBeSpreadOn(block));
    }

    private boolean canBlockBeSpreadOn(Block block) {
        return block.solid() && !canBlockNotBeSpreadOn(block);
    }

    private boolean canBlockNotBeSpreadOn(Block block) {
        return block.blockType() == BlockType.SHULKER_BOX ||
            block.blockType() == BlockType.BEACON ||
            block.blockType() == BlockType.CAULDRON ||
            block.blockType() == BlockType.GLASS ||
            block.blockType() == BlockType.STAINED_GLASS ||
            block.blockType() == BlockType.PISTON ||
            block.blockType() == BlockType.STICKY_PISTON ||
            block.blockType() == BlockType.PISTON_HEAD ||
            block.blockType() == BlockType.TRAPDOOR;
    }

    private int amountOfVines(int x, int y, int z) {
        int amount = 0;

        int xStep = MathUtils.fastFloor(x / 2f);
        int yStep = MathUtils.fastFloor(y / 2f);
        int zStep = MathUtils.fastFloor(z / 2f);

        BlockPosition base = this.location.toBlockPosition();
        for (int x1 = -xStep; x1 <= xStep; x1++) {
            for (int z1 = -zStep; z1 <= xStep; z1++) {
                for (int y1 = -yStep; y1 <= yStep; y1++) {
                    if (this.world.blockAt(base.clone().add(x1, y1, z1)).blockType() == BlockType.VINES) {
                        amount++;
                    }
                }
            }
        }

        return amount;
    }

    public void attach(Facing facing) {
        ATTACHED_SIDES.enable(this, facing);
    }

}
