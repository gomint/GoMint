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
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityWolf extends EntityAgeable<EntityWolf> {

    /**
     * Create a new entity wolf with no config
     *
     * @return empty, fresh wolf
     */
    static EntityWolf create() {
        return GoMint.instance().createEntity( EntityWolf.class );
    }

}
