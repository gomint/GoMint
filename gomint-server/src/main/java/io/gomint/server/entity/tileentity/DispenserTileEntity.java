/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.math.Location;
import io.gomint.server.inventory.DispenserInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class DispenserTileEntity extends TileEntity implements InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( DispenserTileEntity.class );
    private final DispenserInventory inventory;

    /**
     * Construct a new dispenser
     *
     * @param items which are inside the dispenser
     * @param location of the dispenser
     */
    public DispenserTileEntity( ItemStack[] items, Location location ) {
        super( location );
        this.inventory = new DispenserInventory( this );

        if ( items != null ) {
            for ( int i = 0; i < items.length; i++ ) {
                ItemStack itemStack = items[i];
                if ( itemStack != null ) {
                    this.inventory.setItem( i, itemStack );
                }
            }
        }
    }

    /**
     * Create a new tile entity based on its stored compound
     *
     * @param compound which holds stored data
     * @param worldAdapter which holds this tile entity
     */
    public DispenserTileEntity( NBTTagCompound compound, WorldAdapter worldAdapter ) {
        super( compound, worldAdapter );
        this.inventory = new DispenserInventory( this );

        // Read in items
        List<Object> itemList = compound.getList( "Items", false );
        if ( itemList == null ) return;

        for ( Object item : itemList ) {
            NBTTagCompound itemCompound = (NBTTagCompound) item;

            io.gomint.server.inventory.item.ItemStack itemStack = getItemStack( itemCompound );
            if ( itemStack instanceof ItemAir ) {
                continue;
            }

            byte slot = itemCompound.getByte( "Slot", (byte) 127 );
            if ( slot == 127 ) {
                LOGGER.warn( "Found item without slot information: {} @ {} setting it to the next free slot", itemStack.getMaterial(), this.location );
                this.inventory.addItem( itemStack );
            } else {
                this.inventory.setItem( slot, itemStack );
            }
        }
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "Dispenser" );

        List<NBTTagCompound> nbtTagCompounds = new ArrayList<>();
        for ( int i = 0; i < this.inventory.size(); i++ ) {
            ItemStack itemStack = (ItemStack) this.inventory.getItem( i );
            if ( itemStack != null ) {
                NBTTagCompound nbtTagCompound = new NBTTagCompound( "" );
                nbtTagCompound.addValue( "Slot", (byte) i );
                putItemStack( itemStack, nbtTagCompound );
                nbtTagCompounds.add( nbtTagCompound );
            }
        }

        compound.addValue( "Items", nbtTagCompounds );
    }

}
