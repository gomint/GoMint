/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.FlowerPotTileEntity;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FlowerPotConverter extends BasisConverter<FlowerPotTileEntity> {

    public FlowerPotConverter( Items items, Object2IntMap<String> itemConverter ) {
        super( items, itemConverter );
    }

    @Override
    public FlowerPotTileEntity readFrom( NBTTagCompound compound ) {
        // Read position
        Location position = getPosition( compound );

        // Read the item inside the pot
        int material;
        try {
            material = MaterialMagicNumbers.valueOfWithId( compound.getString( "Item", "minecraft:air" ) );
        } catch ( ClassCastException e ) {
            material = compound.getInteger( "Item", 0 );
        }

        // Skip non existent items for PE
        if ( material == -1 ) {
            return new FlowerPotTileEntity( this.items.create( 0, (short) 0, (byte) 0, null ), position );
        }

        short data = compound.getInteger( "Data", -1 ).shortValue();
        return new FlowerPotTileEntity( this.items.create( material, data, (byte) 1, null ), position );
    }

}
