package io.gomint.server.entity.tileentity;

import io.gomint.math.Location;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;
import lombok.Setter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ContainerTileEntity extends TileEntity {

    @Getter @Setter
    private String customName = null;

    /**
     * Construct new tile entity
     *
     * @param location of the tile entity
     */
    ContainerTileEntity( Location location ) {
        super( location );
    }

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    ContainerTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        this.customName = tagCompound.getString( "CustomName", null );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        if ( this.customName != null ) {
            compound.addValue( "CustomName", this.customName );
        }
    }

}
