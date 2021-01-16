/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.animal;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityPufferfish extends EntityLiving<EntityPufferfish> {

    /**
     * Create a new entity puffer fish with no config
     *
     * @return empty, fresh puffer fish
     */
    static EntityPufferfish create() {
        return GoMint.instance().createEntity( EntityPufferfish.class );
    }

}
