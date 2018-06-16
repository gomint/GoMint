/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.entity;

import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 * @param <T> type of entity which this converter should generate
 */
public abstract class EntityConverter<T> {

    /**
     * Construct and read a entity from the given compound
     *
     * @param compound which should be read
     * @return entity with config found in the compound
     */
    public abstract T readFrom( NBTTagCompound compound );

}
