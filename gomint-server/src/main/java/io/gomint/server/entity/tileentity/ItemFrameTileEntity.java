/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.math.Vector;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFrameTileEntity extends TileEntity {

    private ItemStack holdingItem;
    private float itemDropChance;
    private byte itemRotation;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public ItemFrameTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        //
        this.itemDropChance = tagCompound.getFloat( "ItemDropChance", 1.0f );
        this.itemRotation = tagCompound.getByte( "ItemRotation", (byte) 0 );

        //
        NBTTagCompound itemCompound = tagCompound.getCompound( "Item", false );
        if ( itemCompound != null ) {
            this.holdingItem = world.getServer().getItems().create(
                itemCompound.getShort( "id", (short) 0 ),
                itemCompound.getShort( "Damage", (short) 0 ),
                itemCompound.getByte( "Count", (byte) 1 ),
                itemCompound.getCompound( "tag", false ) );
        } else {
            this.holdingItem = world.getServer().getItems().create( 0, (short) 0, (byte) 0, null );
        }
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void interact( Entity entity, BlockFace face, Vector facePos, io.gomint.inventory.item.ItemStack item ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "ItemFrame" );
        compound.addValue( "ItemDropChance", this.itemDropChance );
        compound.addValue( "ItemRotation", this.itemRotation );

        NBTTagCompound itemCompound = new NBTTagCompound( "Item" );
        putItemStack( this.holdingItem, itemCompound );
        compound.addValue( "Item", itemCompound );
    }
}
