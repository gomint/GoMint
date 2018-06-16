/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.tileentity.TileEntityConverter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @param <T> type of tile entity which this converter should generate
 * @author geNAZt
 * @version 1.0
 */
public abstract class BasisConverter<T> extends TileEntityConverter<T> {

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public BasisConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    /**
     * Read a position from the compound given
     *
     * @param compound which contains x, y and z position integers
     * @return block position object
     */
    protected Location getPosition( NBTTagCompound compound ) {
        return new Location(
            this.worldAdapter,
            compound.getInteger( "x", 0 ),
            compound.getInteger( "y", -1 ),
            compound.getInteger( "z", 0 )
        );
    }

    /**
     * Get the item out of the compound
     *
     * @param compound which has serialized information about the item stack
     * @return the item stack which has been stored in the compound
     */
    protected ItemStack getItemStack( NBTTagCompound compound ) {
        // Check for correct ids
        int material = 0;
        try {
            material = MaterialMagicNumbers.valueOfWithId( compound.getString( "id", "minecraft:air" ) );
        } catch ( ClassCastException e ) {
            material = compound.getShort( "id", (short) 0 );
        }

        // Skip non existent items for PE
        if ( material == 0 ) {
            return this.worldAdapter.getServer().getItems().create( 0, (short) 0, (byte) 0, null );
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 1 );

        return this.worldAdapter.getServer().getItems().create( material, data, amount, compound.getCompound( "tag", false ) );
    }

}
