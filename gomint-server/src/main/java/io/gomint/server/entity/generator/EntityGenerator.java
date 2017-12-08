/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.generator;

import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityGenerator {

    /**
     * Create a new entity
     *
     * @param <T> generic type of entity
     */
    <T extends Entity> T generate();

}
