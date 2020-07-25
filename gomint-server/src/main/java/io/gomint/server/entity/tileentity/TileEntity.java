/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.collection.FreezableSortedMap;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.SortedMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class TileEntity {

    // CHECKSTYLE:OFF
    @Getter
    protected Block block;
    @Autowired
    protected Items items;
    private byte moveable;
    protected boolean needsPersistence;
    // CHECKSTYLE:ON

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    TileEntity( Block block ) {
        this.block = block;
        this.moveable = 1;
    }

    BlockIdentifier getBlockIdentifier( NBTTagCompound compound ) {
        if ( compound == null ) {
            return null;
        }

        // TODO: Check for correct NBT tag values
        return BlockRuntimeIDs.toBlockIdentifier( compound.getString( "name", "minecraft:air" ), null);
    }

    void putBlockIdentifier( BlockIdentifier identifier, NBTTagCompound compound ) {
        compound.addValue( "name", identifier.getBlockId() );
        // compound.addValue( "val", identifier.getData() );
    }

    io.gomint.server.inventory.item.ItemStack getItemStack( NBTTagCompound compound ) {
        // Item not there?
        if ( compound == null ) {
            return this.items.create( 0, (short) 0, (byte) 0, null );
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 0 );

        // This is needed since minecraft changed from storing raw ids to string keys somewhere in 1.7 / 1.8
        try {
            return this.items.create( compound.getShort( "id", (short) 0 ), data, amount, compound.getCompound( "tag", false ) );
        } catch ( ClassCastException e ) {
            try {
                return this.items.create( compound.getString( "id", "minecraft:air" ), data, amount, compound.getCompound( "tag", false ) );
            } catch ( ClassCastException e1 ) {
                return this.items.create( compound.getInteger( "id", 0 ), data, amount, compound.getCompound( "tag", false ) );
            }
        }
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
     * Tick this tileEntity exactly once per 50 ms
     *
     * @param currentMillis The amount of millis to save some CPU
     */
    public abstract void update( long currentMillis );

    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {

    }

    /**
     * Load this tile entity from a compound
     *
     * @param compound which holds data for this tile entity
     */
    public void fromCompound( NBTTagCompound compound ) {
        this.moveable = compound.getByte( "isMovable", (byte) 1 );
    }

    /**
     * Save this TileEntity back to an compound
     *
     * @param compound The Compound which should be used to save the data into
     * @param reason   why should this tile be serialized?
     */
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        BlockPosition position = this.block.getLocation().toBlockPosition();

        compound.addValue( "x", position.getX() );
        compound.addValue( "y", position.getY() );
        compound.addValue( "z", position.getZ() );

        if ( reason == SerializationReason.PERSIST ) {
            compound.addValue( "isMovable", this.moveable );
        }
    }

    public boolean isNeedsPersistence() {
        return this.needsPersistence;
    }

    public void resetPersistenceFlag() {
        this.needsPersistence = false;
    }

    public void applyClientData( EntityPlayer player, NBTTagCompound compound ) throws Exception {

    }

}
