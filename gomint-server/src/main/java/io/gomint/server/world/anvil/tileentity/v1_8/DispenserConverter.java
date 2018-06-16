/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.entity.tileentity.DispenserTileEntity;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
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
public class DispenserConverter extends BasisConverter<DispenserTileEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger( DispenserConverter.class );

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public DispenserConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    @Override
    public DispenserTileEntity readFrom( NBTTagCompound compound ) {
        // Read position first
        Location position = getPosition( compound );

        // Read in items
        ItemStack[] items = new ItemStack[9];
        List<Object> itemList = compound.getList( "Items", false );
        if ( itemList == null ) {
            // No items ? Return empty chest
            return new DispenserTileEntity( items, position );
        }

        // Iterate over all items
        for ( Object item : itemList ) {
            NBTTagCompound itemCompound = (NBTTagCompound) item;

            ItemStack itemStack = getItemStack( itemCompound );
            if ( itemStack instanceof ItemAir ) {
                continue;
            }

            byte slot = itemCompound.getByte( "Slot", (byte) 127 );
            if ( slot == 127 ) {
                LOGGER.warn( "Found item without slot information: {} @ {} setting it to the next free slot", itemStack.getMaterial(), position );
                for ( int i = 0; i < items.length; i++ ) {
                    ItemStack freeItem = items[i];
                    if ( freeItem == null ) {
                        items[i] = itemStack;
                        break;
                    }
                }
            } else {
                items[slot] = itemStack;
            }
        }

        return new DispenserTileEntity( items, position );
    }

}
