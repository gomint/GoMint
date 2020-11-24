/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.math.MathUtils;
import io.gomint.server.world.block.Block;

import java.util.function.Supplier;

public class RedstoneSignalStrength extends ProgressBlockState {

    public RedstoneSignalStrength(Supplier<String[]> key) {
        super(key, 15, aVoid -> {});
    }

    public void decrease(Block block) {
        if (this.getState(block) - this.getStep() <= MathUtils.EPSILON) {
            this.setState(block,this.getState(block) - this.getStep());
        }
    }

    public void increase(Block block) {
        if (1f - this.getState(block) <= MathUtils.EPSILON) {
            this.setState(block, this.getState(block) + this.getStep());
        }
    }

    public void on(Block block) {
        this.setState(block,1f);
    }

    public void off(Block block) {
        this.setState(block,0f);
    }

}
