package io.gomint.server.entity.tileentity;

import io.gomint.math.Location;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EndPortalTileEntity extends ContainerTileEntity implements InventoryHolder {

    /**
     * Create a new enchant table
     *
     * @param position of the enchant table
     */
    public EndPortalTileEntity( Location position ) {
        super( position );
    }

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public EndPortalTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );
    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "EndPortal" );
    }

}
