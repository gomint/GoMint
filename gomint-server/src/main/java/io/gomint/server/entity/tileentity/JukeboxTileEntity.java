package io.gomint.server.entity.tileentity;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class JukeboxTileEntity extends TileEntity {

    private ItemStack recordItem;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public JukeboxTileEntity( Block block ) {
        super( block );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        this.recordItem = getItemStack( compound.getCompound( "RecordItem", false ) );
    }

    @Override
    public void update( long currentMillis ) {

    }

    public void setRecordItem( ItemStack recordItem ) {
        this.recordItem = recordItem;
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "Jukebox" );

        if ( this.recordItem.getItemType() != ItemType.AIR ) {
            NBTTagCompound itemCompound = compound.getCompound( "RecordItem", true );
            this.putItemStack( this.recordItem, itemCompound );
        }
    }

}
