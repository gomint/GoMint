package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "DaylightDetector")
public class DaylightDetectorTileEntity extends TileEntity {

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public DaylightDetectorTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void update( long currentMillis, float dT ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "DaylightDetector" );
    }

}
