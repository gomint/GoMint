/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.ChestInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ChestTileEntity extends TileEntity implements InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( ChestTileEntity.class );
    private ChestInventory inventory;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public ChestTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );
        this.inventory = new ChestInventory( this );

        // Read in items
        List<Object> itemList = tagCompound.getList( "Items", false );
        if ( itemList == null ) return;

        for ( Object item : itemList ) {
            NBTTagCompound itemCompound = (NBTTagCompound) item;

            // This is needed since minecraft changed from storing raw ids to string keys somewhere in 1.7 / 1.8
            int material = 0;
            try {
                material = itemCompound.getShort( "id", (short) 0 );
            } catch ( ClassCastException e ) {
                material = MaterialMagicNumbers.valueOfWithId( itemCompound.getString( "id", "minecraft:air" ) );
            }

            // Skip non existent items for PE
            if ( material == 0 ) {
                continue;
            }

            byte slot = itemCompound.getByte( "Slot", (byte) 127 );
            if ( slot == 127 ) {
                LOGGER.warn( "Found item without slot information: " + material + " @ " + this.location + " setting it to the next free slot" );
                this.inventory.addItem( Items.create( material,
                        itemCompound.getShort( "Damage", (short) 0 ),
                        itemCompound.getByte( "Count", (byte) 1 ), null ) );
            } else {
                this.inventory.setItem( slot, Items.create( material,
                        itemCompound.getShort( "Damage", (short) 0 ),
                        itemCompound.getByte( "Count", (byte) 1 ), null ) );
            }
        }
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void interact( Entity entity, int face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            ( (EntityPlayer) entity ).openInventory( this.inventory );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );
        compound.addValue( "id", "Chest" );

        List<NBTTagCompound> nbtTagCompounds = new ArrayList<>();
        for ( int i = 0; i < this.inventory.size(); i++ ) {
            ItemStack itemStack = this.inventory.getItem( i );
            if ( itemStack != null ) {
                NBTTagCompound nbtTagCompound = new NBTTagCompound( "" );
                nbtTagCompound.addValue( "Slot", (byte) i );
                nbtTagCompound.addValue( "id", ( (io.gomint.server.inventory.item.ItemStack) itemStack ).getMaterial() );
                nbtTagCompound.addValue( "Damage", itemStack.getData() );
                nbtTagCompound.addValue( "Count", itemStack.getAmount() );
                nbtTagCompounds.add( nbtTagCompound );
            }
        }

        compound.addValue( "Items", nbtTagCompounds );
    }
}
