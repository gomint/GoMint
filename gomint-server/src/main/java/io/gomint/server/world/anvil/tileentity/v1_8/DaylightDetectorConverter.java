/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.DaylightDetectorTileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DaylightDetectorConverter extends BasisConverter<DaylightDetectorTileEntity> {

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public DaylightDetectorConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    @Override
    public DaylightDetectorTileEntity readFrom( NBTTagCompound compound ) {
        Location position = getPosition( compound );
        return new DaylightDetectorTileEntity( position );
    }

}
