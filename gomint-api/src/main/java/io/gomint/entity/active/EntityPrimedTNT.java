/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.active;

import io.gomint.GoMint;
import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityPrimedTNT extends Entity<EntityPrimedTNT> {

    /**
     * Create a new EntityPrimedTNT without any configuration. It will use default values when not configured and spawned
     *
     * @return new entity
     */
    static EntityPrimedTNT create() {
        return GoMint.instance().createEntity( EntityPrimedTNT.class );
    }

    /**
     * Set new fuse time
     *
     * @param fuseInSeconds fuse time in seconds
     */
    EntityPrimedTNT fuse(float fuseInSeconds );

    /**
     * Get time until explosion in seconds
     *
     * @return time until explosion
     */
    float fuse();

}
