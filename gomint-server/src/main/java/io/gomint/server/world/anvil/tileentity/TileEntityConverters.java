/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity;

import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface TileEntityConverters {

    /**
     * Convert a tile entity to the PE format
     *
     * @param compound which should be read
     * @return constructed tile entity
     */
    TileEntity read( NBTTagCompound compound );

}
