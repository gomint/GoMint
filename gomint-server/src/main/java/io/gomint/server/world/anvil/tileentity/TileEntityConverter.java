/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity;

import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.AllArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 * @param <T> type of tile entity which this converter should generate
 */
@AllArgsConstructor
public abstract class TileEntityConverter<T> {

    /**
     * World for which this converter converts
     */
    protected WorldAdapter worldAdapter;

    /**
     * Construct and read a tile entity from the given compound
     *
     * @param compound which should be read
     * @return entity with config found in the compound
     */
    public abstract T readFrom( NBTTagCompound compound );

}
