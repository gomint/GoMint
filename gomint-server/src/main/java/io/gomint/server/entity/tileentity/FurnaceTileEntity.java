/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.inventory.item.ItemAir;
import io.gomint.math.Vector;
import io.gomint.server.inventory.FurnaceInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FurnaceTileEntity extends ContainerTileEntity implements InventoryHolder {

    private FurnaceInventory inventory;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public FurnaceTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        this.inventory = new FurnaceInventory( this );

        List<Object> itemCompounds = tagCompound.getList( "Items", false );
        if ( itemCompounds != null ) {
            for ( Object itemCompound : itemCompounds ) {
                NBTTagCompound cd = (NBTTagCompound) itemCompound;

                byte slot = cd.getByte( "Slot", (byte) -1 );
                if ( slot == -1 ) {
                    this.inventory.addItem( getItemStack( cd ) );
                } else {
                    this.inventory.setItem( slot, getItemStack( cd ) );
                }
            }
        }
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void interact( Entity entity, BlockFace face, Vector facePos, io.gomint.inventory.item.ItemStack item ) {
        if ( entity instanceof EntityPlayer ) {
            ( (EntityPlayer) entity ).openInventory( this.inventory );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "Furnace" );

        List<NBTTagCompound> itemCompounds = new ArrayList<>();
        for ( int i = 0; i < this.inventory.size(); i++ ) {
            ItemStack itemStack = (ItemStack) this.inventory.getItem( i );
            if ( !( itemStack instanceof ItemAir ) ) {
                NBTTagCompound itemCompound = new NBTTagCompound( "" );
                itemCompound.addValue( "Slot", (byte) i );
                putItemStack( itemStack, itemCompound );
                itemCompounds.add( itemCompound );
            }
        }

        compound.addValue( "Items", itemCompounds );
    }

}
