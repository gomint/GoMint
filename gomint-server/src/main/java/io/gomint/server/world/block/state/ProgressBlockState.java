/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ProgressBlockState extends BlockState<Float, Integer> {

    private Consumer<Block> maxedProgressConsumer;
    private int max;
    private float step;

    public ProgressBlockState(Supplier<String[]> key, int max, Consumer<Block> maxedProgressConsumer) {
        super(v -> key.get());
        this.step = 1f / max;
        this.maxedProgressConsumer = maxedProgressConsumer;
        this.max = max;
    }

    public boolean progress(Block block) {
        this.setState(block, this.getState(block) + this.step);
        if (1f - this.getState(block) <= MathUtils.EPSILON) {
            this.maxedProgressConsumer.accept(block);
            return false;
        }

        return true;
    }

    @Override
    protected void calculateValueFromState(Block block, Float state) {
        this.setValue(block, Math.round(state * this.max));
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving player, ItemStack placedItem, Facing face) {
        this.setState(newBlock, 0f);
    }

    @Override
    public Float getState(Block block) {
        return this.getValue(block) * this.step;
    }

    public boolean maxed(Block block) {
        return 1f - this.getState(block) <= MathUtils.EPSILON;
    }

    public float getStep() {
        return step;
    }
}
