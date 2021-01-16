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
public interface EntityLama extends EntityAgeable<EntityLama> {

    /**
     * Create a new entity lama with no config
     *
     * @return empty, fresh lama
     */
    static EntityLama create() {
        return GoMint.instance().createEntity( EntityLama.class );
    }

}
