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
public interface EntityConverter<T> {

    /**
     * Create a empty entity
     *
     * @return new entity
     */
    T create();

    /**
     * Construct and read a entity from the given compound
     *
     * @param compound which should be read
     * @return entity with config found in the compound
     */
    T readFrom( NBTTagCompound compound );

    /**
     * Write all data which is needed to reconstruct this entity into a new nbt tag compound
     *
     * @param entity which should be saved
     * @return nbt tag compound with all data needed to reconstruct
     */
    NBTTagCompound writeTo( T entity );

}
