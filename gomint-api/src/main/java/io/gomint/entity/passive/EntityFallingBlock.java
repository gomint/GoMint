/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityFallingBlock extends Entity {

    /**
     * Create a new entity falling block with no config
     *
     * @return empty, fresh falling block
     */
    static EntityFallingBlock create() {
        return GoMint.instance().createEntity( EntityFallingBlock.class );
    }

    /**
     * Set the block which this entity should transport
     *
     * @param block which should be transported
     */
    void setBlock( Block block );

}
