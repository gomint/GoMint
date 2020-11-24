/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome.component;

import com.google.common.collect.Lists;
import io.gomint.world.biome.component.Ground;
import io.gomint.world.block.Block;

import java.util.Collections;
import java.util.List;

public class GroundComponent implements Component, Ground {

    private final List<Block> blocks;
    private final int min;
    private final int max;

    public GroundComponent(int min, int max, Block ... blocks) {
        this.blocks = Collections.unmodifiableList(Lists.newArrayList(blocks));
        this.min = min;
        this.max = max;
    }

    public GroundComponent(int min, int max) {
        this.blocks = null;
        this.min = min;
        this.max = max;
    }
    
    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int min() {
        return this.min;
    }

    @Override
    public int max() {
        return this.max;
    }

}
