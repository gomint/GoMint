/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.projectile;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityExpBottle extends EntityProjectile<EntityExpBottle> {

    /**
     * Create a new entity exp bottle
     *
     * @return fresh exp bottle
     */
    static EntityExpBottle create() {
        return GoMint.instance().createEntity( EntityExpBottle.class );
    }

}
