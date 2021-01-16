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
 * @author joserobjr
 * @since 2021-01-12
 */
public interface EntityCat extends EntityAgeable<EntityCat> {
    /**
     * Create a new entity cat with no config
     *
     * @return empty, fresh cat
     */
    static EntityCat create() {
        return GoMint.instance().createEntity( EntityCat.class );
    }
}
