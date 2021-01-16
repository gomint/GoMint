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
public interface EntityDonkey extends EntityAgeable<EntityDonkey> {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh donkey
     */
    static EntityDonkey create() {
        return GoMint.instance().createEntity( EntityDonkey.class );
    }

}
