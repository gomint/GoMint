/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
public interface EntityPiglinBrute extends EntityLiving {

    /**
     * Create a new entity piglin brute with no config
     *
     * @return empty, fresh piglin brute
     */
    static EntityPiglinBrute create() {
        return GoMint.instance().createEntity( EntityPiglinBrute.class );
    }
}
