/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.math.MathUtils;
import io.gomint.server.world.block.Block;

import java.util.function.Supplier;

public class RedstoneSignalStrength extends ProgressBlockState {

    public RedstoneSignalStrength(Block block, Supplier<String> key) {
        super(block, key, 15, aVoid -> {
        });
    }

    public void decrease() {
        if (this.getState() - this.getStep() <= MathUtils.EPSILON) {
            this.setState(this.getState() - this.getStep());
        }
    }

    public void increase() {
        if (1f - this.getState() <= MathUtils.EPSILON) {
            this.setState(this.getState() + this.getStep());
        }
    }

    public void on() {
        this.setState(1f);
    }

    public void off() {
        this.setState(0f);
    }

}
