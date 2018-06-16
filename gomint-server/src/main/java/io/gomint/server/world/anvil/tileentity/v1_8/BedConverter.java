/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.BedTileEntity;
import io.gomint.server.entity.tileentity.EnchantTableTileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BedConverter extends BasisConverter<BedTileEntity> {

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public BedConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    @Override
    public BedTileEntity readFrom( NBTTagCompound compound ) {
        // Read position
        Location position = getPosition( compound );

        return new BedTileEntity( BlockColor.RED, position );
    }

}
