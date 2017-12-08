/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FlowerPotTileEntity extends TileEntity implements InventoryHolder {

    private ItemStack holdingItem;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public FlowerPotTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        // This is needed since minecraft changed from storing raw ids to string keys somewhere in 1.7 / 1.8
        int material = 0;
        if ( tagCompound.containsKey( "item" ) ) {
             material = tagCompound.getShort( "item", (short) 0 );
        } else {
            try {
                material = tagCompound.getInteger( "Item", 0 );
            } catch ( ClassCastException e ) {
                material = MaterialMagicNumbers.valueOfWithId( tagCompound.getString( "Item", "minecraft:air" ) );
            }
        }

        // Skip non existent items for PE
        if ( material == 0 ) {
            this.holdingItem = Items.create( 0, (short) 0, (byte) 1, null );
            return;
        }

        short data = tagCompound.getInteger( "mData", -1 ).shortValue();
        if ( data == -1 ) {
            data = tagCompound.getInteger( "Data", -1 ).shortValue();
        }

        this.holdingItem = Items.create( material, data, (byte) 1, null );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void interact( Entity entity, int face, Vector facePos, io.gomint.inventory.item.ItemStack item ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );
        compound.addValue( "id", "FlowerPot" );
        compound.addValue( "item", (short) this.holdingItem.getMaterial() );
        compound.addValue( "mData", (int) this.holdingItem.getData() );
    }
}
