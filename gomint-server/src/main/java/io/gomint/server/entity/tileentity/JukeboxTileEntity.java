package io.gomint.server.entity.tileentity;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Location;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class JukeboxTileEntity extends TileEntity {

    private ItemStack recordItem;

    public JukeboxTileEntity( NBTTagCompound compound, WorldAdapter worldAdapter, Items items ) {
        super( compound, worldAdapter, items );

        this.recordItem = getItemStack( compound.getCompound( "RecordItem", false ) );
    }

    public JukeboxTileEntity( ItemStack itemStack, Location position ) {
        super( position );

        this.recordItem = itemStack;
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

        if ( this.recordItem.getType() != ItemType.AIR ) {
            NBTTagCompound itemCompound = compound.getCompound( "RecordItem", true );
            this.putItemStack( this.recordItem, itemCompound );
        }
    }

}
