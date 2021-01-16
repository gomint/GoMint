/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.animal;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityStrider extends EntityAgeable<EntityStrider> {

    /**
     * Create a new entity strider with no config
     *
     * @return empty, fresh zombie strider
     */
    static EntityStrider create() {
        return GoMint.instance().createEntity( EntityStrider.class );
    }
}
