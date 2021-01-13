/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.entity.passive.EntityFallingBlock;
import io.gomint.server.world.UpdateReason;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Fallable extends Block {

    private static final Logger LOGGER = LoggerFactory.getLogger(Fallable.class);

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        LOGGER.debug("Updating falling state for {}", this.location);

        // Check if the downside block can be replaced
        Block downwards = this.side(Facing.DOWN);
        if (downwards.canBeReplaced(null)) {
            // Create entity
            EntityFallingBlock entity = EntityFallingBlock.create();
            entity.setBlock(this);

            // Replace block with air
            this.blockType(Air.class);

            // Spawn entity
            entity.spawn(this.location.add(0.5f, 0, 0.5f));
        }

        return -1;
    }

}
