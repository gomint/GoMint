/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.SkullTileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SkullConverter extends BasisConverter<SkullTileEntity> {

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public SkullConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    @Override
    public SkullTileEntity readFrom( NBTTagCompound compound ) {
        // Read position first
        Location position = this.getPosition( compound );

        // We only need the skull type and rotation
        byte rotation = compound.getByte( "Rot", (byte) 0 );
        byte skullType = compound.getByte( "SkullType", (byte) 0 );

        return new SkullTileEntity( rotation, skullType, position );
    }

}
