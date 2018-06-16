/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.mcpe;

import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.tileentity.TileEntityConverters;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TileEntities implements TileEntityConverters {

    private final WorldAdapter world;

    /**
     * Construct 1.8 converter for the given world
     *
     * @param worldAdapter which should be used in all convert operations
     */
    public TileEntities( WorldAdapter worldAdapter ) {
        this.world = worldAdapter;
    }

    @Override
    public TileEntity read( NBTTagCompound compound ) {
        return io.gomint.server.entity.tileentity.TileEntities.construct( compound, this.world );
    }

}
