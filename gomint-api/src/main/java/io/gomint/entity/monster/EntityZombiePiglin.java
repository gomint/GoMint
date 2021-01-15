/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
public interface EntityZombiePiglin extends EntityAgeable<EntityZombiePiglin> {
    
    /**
     * Create a new entity zombie piglin with no config
     *
     * @return empty, fresh zombie piglin
     */
    static EntityZombiePiglin create() {
        return GoMint.instance().createEntity( EntityZombiePiglin.class );
    }
}
