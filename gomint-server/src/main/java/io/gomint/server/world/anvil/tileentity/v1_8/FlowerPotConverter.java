/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.FlowerPotTileEntity;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FlowerPotConverter extends BasisConverter<FlowerPotTileEntity> {

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public FlowerPotConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
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
        if ( material == 0 ) {
            return new FlowerPotTileEntity( this.worldAdapter.getServer().getItems().create( 0, (short) 0, (byte) 0, null ), position );
        }

        short data = compound.getInteger( "Data", -1 ).shortValue();
        return new FlowerPotTileEntity( this.worldAdapter.getServer().getItems().create( material, data, (byte) 1, null ), position );
    }

}
