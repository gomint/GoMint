/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.math.Location;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BannerTileEntity extends TileEntity {

    public BannerTileEntity( Location location ) {
        super( location );
    }

    public BannerTileEntity( NBTTagCompound compound, WorldAdapter worldAdapter ) {
        super( compound, worldAdapter );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

}
