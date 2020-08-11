/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemAir;
import io.gomint.math.Vector;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFrameTileEntity extends TileEntity {

    private ItemStack holdingItem = (ItemStack) ItemAir.create( 0 );
    private float itemDropChance = 1f;
    private float itemRotation;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public ItemFrameTileEntity( Block block ) {
        super( block );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        //
        this.itemDropChance = compound.getFloat( "ItemDropChance", 1.0f );
        this.itemRotation = compound.getFloat( "ItemRotation", 0f );

        //
        this.holdingItem = getItemStack( compound.getCompound( "Item", false ) );
    }

    @Override
    public void update( long currentMillis ) {

    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, io.gomint.inventory.item.ItemStack item ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "ItemFrame" );

        if ( reason == SerializationReason.PERSIST ) {
            compound.addValue( "ItemDropChance", this.itemDropChance );
        }

        compound.addValue( "ItemRotation", this.itemRotation );

        NBTTagCompound itemCompound = new NBTTagCompound( "Item" );
        putItemStack( this.holdingItem, itemCompound );
        compound.addValue( "Item", itemCompound );
    }
}
