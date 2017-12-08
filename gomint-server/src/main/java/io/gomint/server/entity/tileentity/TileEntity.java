/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class TileEntity {

    // CHECKSTYLE:OFF
    @Getter protected Location location;
    private byte moveable;
    // CHECKSTYLE:ON

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    TileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        this.location = new Location(
            world,
            tagCompound.getInteger( "x", 0 ),
            tagCompound.getInteger( "y", -1 ),
            tagCompound.getInteger( "z", 0 )
        );

        this.moveable = tagCompound.getByte( "isMoveable", (byte) 1 );
    }

    io.gomint.server.inventory.item.ItemStack getItemStack( NBTTagCompound compound ) {
        // Check for correct ids

        // This is needed since minecraft changed from storing raw ids to string keys somewhere in 1.7 / 1.8
        int material;
        try {
            material = compound.getShort( "id", (short) 0 );
        } catch ( ClassCastException e ) {
            material = MaterialMagicNumbers.valueOfWithId( compound.getString( "id", "minecraft:air" ) );
        }

        // Skip non existent items for PE
        if ( material == 0 ) {
            return Items.create( 0, (short) 0, (byte) 0, null );
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 1 );

        return Items.create( material, data, amount, compound.getCompound( "tag", false ) );
    }


    void putItemStack( io.gomint.server.inventory.item.ItemStack itemStack, NBTTagCompound compound ) {
        compound.addValue( "id", (short) itemStack.getMaterial() );
        compound.addValue( "Damage", itemStack.getData() );
        compound.addValue( "Count", itemStack.getAmount() );

        if ( itemStack.getNbtData() != null ) {
            NBTTagCompound itemTag = itemStack.getNbtData().deepClone( "tag" );
            compound.addValue( "tag", itemTag );
        }
    }

    /**
     * Tick this tileEntity
     *
     * @param currentMillis The amount of millis to save some CPU
     * @param dF            The percentage of the second which was calculated in the last tick
     */
    public abstract void update( long currentMillis, float dF );

    public void interact( Entity entity, int face, Vector facePos, ItemStack item ) {

    }

    /**
     * Save this TileEntity back to an compound
     *
     * @param compound The Compound which should be used to save the data into
     */
    public void toCompound( NBTTagCompound compound ) {
        compound.addValue( "x", (int) this.location.getX() );
        compound.addValue( "y", (int) this.location.getY() );
        compound.addValue( "z", (int) this.location.getZ() );
        compound.addValue( "isMoveable", this.moveable );
    }

}
