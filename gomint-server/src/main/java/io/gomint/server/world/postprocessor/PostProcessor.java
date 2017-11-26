/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.postprocessor;

import io.gomint.math.BlockPosition;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import lombok.RequiredArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class PostProcessor {

    // CHECKSTYLE:OFF
    protected final WorldAdapter worldAdapter;
    protected final BlockPosition position;
    // CHECKSTYLE:ON

    /**
     * Get the block affected by this processor
     *
     * @return block in this processor
     */
    Block getBlock() {
        return this.worldAdapter.getBlockAt( this.position );
    }

    /**
     * Process a block for post loading (fixing tile entities etc.)
     */
    public abstract void process();

}
