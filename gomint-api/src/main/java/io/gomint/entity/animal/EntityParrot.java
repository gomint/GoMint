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
public interface EntityParrot extends EntityLiving<EntityParrot> {

    /**
     * Create new entity parrot with no config
     *
     * @return empty, fresh parrot
     */
    static EntityParrot create() {
        return GoMint.instance().createEntity( EntityParrot.class );
    }

    /**
     * Set this parrot dancing
     *
     * @param value true if this parrot should be dancing, false if not
     */
    EntityParrot dancing(boolean value );

    /**
     * Is the parrot dancing?
     *
     * @return true if this parrot is dancing, false if not
     */
    boolean dancing();

}
