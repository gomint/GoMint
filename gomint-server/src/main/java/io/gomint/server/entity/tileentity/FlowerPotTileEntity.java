/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockFace;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class FlowerPotTileEntity extends TileEntity implements InventoryHolder {

    private ItemStack holdingItem;

    /**
     * Create a new flower pot with the item given as content
     *
     * @param itemStack which should be inside the flower pot
     * @param position  of the flower pot
     */
    public FlowerPotTileEntity( ItemStack itemStack, Location position ) {
        super( position );
        this.holdingItem = itemStack;
    }

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public FlowerPotTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        int material = tagCompound.getShort( "item", (short) 0 );

        // Skip non existent items for PE
        if ( material == 0 ) {
            this.holdingItem = world.getServer().getItems().create( 0, (short) 0, (byte) 1, null );
            return;
        }

        short data = tagCompound.getInteger( "mData", -1 ).shortValue();
        if ( data == -1 ) {
            data = tagCompound.getInteger( "Data", -1 ).shortValue();
        }

        this.holdingItem = world.getServer().getItems().create( material, data, (byte) 1, null );
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
        compound.addValue( "id", "FlowerPot" );
        compound.addValue( "item", (short) this.holdingItem.getMaterial() );
        compound.addValue( "mData", (int) this.holdingItem.getData() );
    }
}
