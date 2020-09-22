/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.HopperInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Hopper")
public class HopperTileEntity extends TileEntity implements InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( HopperTileEntity.class );

    private int transferCooldown;
    private HopperInventory inventory;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public HopperTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.transferCooldown = compound.getInteger( "TransferCooldown", 0 );
        this.inventory = new HopperInventory( this.items, this );

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
                LOGGER.warn( "Found item without slot information: {} @ {} setting it to the next free slot", itemStack.getMaterial(), this.getBlock().getPosition() );
                this.inventory.addItem( itemStack );
            } else {
                this.inventory.setItem( slot, itemStack );
            }
        }
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "Hopper" );
        compound.addValue( "TransferCooldown", this.transferCooldown );

        if ( reason == SerializationReason.PERSIST ) {
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

    public HopperInventory getInventory() {
        return inventory;
    }

}
