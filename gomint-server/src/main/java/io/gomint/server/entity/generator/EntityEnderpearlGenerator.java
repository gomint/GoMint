/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.generator;

import io.gomint.server.entity.Entity;

/**
 * @author generated
 * @version 2.0
 */
public class EntityEnderpearlGenerator implements EntityGenerator<Entity> {

    public io.gomint.entity.Entity generate() {
         return new io.gomint.server.entity.projectile.EntityEnderpearl();
    }

}