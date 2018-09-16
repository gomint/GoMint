/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.BeaconTileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BeaconConverter extends BasisConverter<BeaconTileEntity> {

    public BeaconConverter( Items items, Object2IntMap<String> itemConverter ) {
        super( items, itemConverter );
    }

    @Override
    public BeaconTileEntity readFrom( NBTTagCompound compound ) {
        Location position = getPosition( compound );

        int primary = compound.getInteger( "Primary", 0 );
        int secondary = compound.getInteger( "Secondary", 0 );

        return new BeaconTileEntity( primary, secondary, position );
    }

}
