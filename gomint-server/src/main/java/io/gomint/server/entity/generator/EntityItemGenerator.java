/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.generator;

import io.gomint.server.entity.Entity;
import io.gomint.server.entity.passive.EntityItem;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityItemGenerator implements EntityGenerator<Entity> {

    @Override
    public EntityItem generate() {
        return new EntityItem();
    }

}
