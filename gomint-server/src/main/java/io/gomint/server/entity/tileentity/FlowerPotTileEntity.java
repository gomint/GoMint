/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.math.Vector;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class FlowerPotTileEntity extends TileEntity implements InventoryHolder {

    private BlockIdentifier holding;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public FlowerPotTileEntity( Block block ) {
        super( block );
    }


    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.holding = getBlockIdentifier( compound.getCompound( "PlantBlock", false ) );
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

        compound.addValue( "id", "FlowerPot" );

        if ( this.holding != null ) {
            NBTTagCompound block = compound.getCompound( "PlantBlock", true );
            putBlockIdentifier( this.holding, block );
        }
    }

}
