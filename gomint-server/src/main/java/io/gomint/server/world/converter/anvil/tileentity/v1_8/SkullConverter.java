/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.SkullTileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SkullConverter extends BasisConverter<SkullTileEntity> {

    public SkullConverter( Items items, Object2IntMap<String> itemConverter ) {
        super( items, itemConverter );
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
