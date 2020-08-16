package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ContainerTileEntity extends TileEntity {

    private String customName = null;

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    ContainerTileEntity( Block block, Items items ) {
        super( block, items );
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.customName = compound.getString( "CustomName", null );
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        if ( this.customName != null ) {
            compound.addValue( "CustomName", this.customName );
        }
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

}
