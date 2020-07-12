package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemVines;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.state.AttachingBlockState;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.BlockType;

import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:vine")
public class Vines extends Block implements io.gomint.world.block.BlockVines {

    private static final String[] DIRECTION_KEY = new String[]{"vine_direction_bits"};
    private static final Facing[] FACES_TO_CHECK = new Facing[]{Facing.EAST, Facing.WEST, Facing.NORTH, Facing.SOUTH};

    private final AttachingBlockState attachedSides = new AttachingBlockState(this, () -> DIRECTION_KEY);

    @Override
    public String getBlockId() {
        return "minecraft:vine";
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
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
    public void stepOn(Entity entity) {
        // Reset fall distance
        entity.resetFallDistance();
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.VINES;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        if (isCorrectTool(itemInHand)) {
            return new ArrayList<>() {{
                add(ItemVines.create(1));
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public boolean beforePlacement(Entity entity, ItemStack item, Facing face, Location location) {
        return face != Facing.UP && face != Facing.DOWN;
    }

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        if (updateReason == UpdateReason.RANDOM) {
            if (FastRandom.current().nextFloat() <= 0.25) {
                Block down = this.getSide(Facing.DOWN);
                for (Facing facing : FACES_TO_CHECK) {
                    // Check if we can grow to the bottom block
                    if (this.attachedSides.enabled(facing) && down.getBlockType() == BlockType.AIR) {
                        if (FastRandom.current().nextFloat() <= 0.5) {
                            Vines downVines = down.setBlockType(Vines.class);
                            downVines.attach(facing);
                        }
                    }
                }
            }
        }

        return -1;
    }

    public void attach(Facing facing) {
        this.attachedSides.enable(facing);
    }

}
