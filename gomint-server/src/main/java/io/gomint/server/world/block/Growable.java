package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.util.random.WeightedRandom;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 * <p>
 * This is a generic class for anything that can grow with metadata like crops, beetroot etc.
 */
public abstract class Growable extends Block {

    /**
     * Randomizer for seed drop output
     */
    protected static final WeightedRandom<Integer> SEED_RANDOMIZER = new WeightedRandom<>();

    static {
        SEED_RANDOMIZER.add(0.15, 0);
        SEED_RANDOMIZER.add(0.35, 1);
        SEED_RANDOMIZER.add(0.35, 2);
        SEED_RANDOMIZER.add(0.15, 3);
    }

    protected static final ProgressBlockState GROWTH = new ProgressBlockState(() -> new String[]{"growth"}, 7, aVoid -> {
    });

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        // Check if we place on farmland
        return location.getWorld().getBlockAt(location.toBlockPosition().add(BlockPosition.DOWN)).getBlockType() == BlockType.FARMLAND;
    }

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        if (updateReason == UpdateReason.NEIGHBOUR_UPDATE) {
            // Check if farmland is still under us
            if (this.world.getBlockAt(this.location.toBlockPosition().add(BlockPosition.DOWN)).getBlockType() != BlockType.FARMLAND) {
                this.world.breakBlock(this.location.toBlockPosition(), new ArrayList<>(), false);
            }
        } else if (updateReason == UpdateReason.RANDOM) {
            this.grow();
        }

        return -1;
    }

    protected void grow() {
        // Check for growth state
        if (GROWTH.getState(this) < 1f) {
            float growthDivider = getGrowthDivider();
            int random = ThreadLocalRandom.current().nextInt((int) ((25f / growthDivider) + 1));

            // Grow it
            if (random == 0) {
                // TODO: Some sort of growth event
                GROWTH.progress(this);
            }
        }
    }

    protected float getGrowthDivider() {
        float divider = 1f;
        BlockPosition underCrops = this.location.toBlockPosition().add(BlockPosition.DOWN);

        // Check for farmland blocks around (hydration states)
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                float currentBlockDivider = 0f;

                Block block = this.world.getBlockAt(underCrops.add(new BlockPosition(x, 0, z)));
                if (block instanceof Farmland) {
                    currentBlockDivider = 1f;

                    if (((Farmland) block).getMoisture() > MathUtils.EPSILON) {
                        currentBlockDivider = 3f;
                    }
                }

                // Surrounding blocks affect only to 25%
                if (x != 0 || z != 0) {
                    currentBlockDivider /= 4f;
                }

                divider += currentBlockDivider;
            }
        }

        // Check if there are similar crops around (slow down bigger farms)
        // We check in a pattern which goes up and clockwise around
        BlockPosition start = this.location.toBlockPosition().add(BlockPosition.NORTH);
        if (this.world.getBlockAt(start).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.EAST)).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.SOUTH)).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.SOUTH)).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.WEST)).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.WEST)).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.NORTH)).getBlockType() == getBlockType() ||
            this.world.getBlockAt(start.add(BlockPosition.NORTH)).getBlockType() == getBlockType()) {
            divider /= 2f;
        }

        return divider;
    }

}
