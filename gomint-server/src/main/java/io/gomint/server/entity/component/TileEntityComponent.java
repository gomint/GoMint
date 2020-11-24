/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.taglib.NBTTagCompound;

public interface TileEntityComponent extends EntityComponent {

    /**
     * Save this TileEntity back to an compound
     *
     * @param compound The Compound which should be used to save the data into
     * @param reason   why should this tile be serialized?
     */
    void toCompound(NBTTagCompound compound, SerializationReason reason );

    /**
     * Load this tile entity from a compound
     *
     * @param compound which holds data for this tile entity
     */
    void fromCompound( NBTTagCompound compound );

}
