package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.ChestInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ShulkerBoxTileEntity extends ContainerTileEntity implements InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( ShulkerBoxTileEntity.class );
    private ChestInventory inventory;
    private boolean undyed = true;
    private byte facing = 1;

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    public ShulkerBoxTileEntity( Block block ) {
        super( block );
        this.inventory = new ChestInventory( this );
    }

    @Override
    public void fromCompound( NBTTagCompound compound ) {
        super.fromCompound( compound );

        //
        this.undyed = compound.getByte( "isUndyed", (byte) 1 ) > 0;
        this.facing = compound.getByte( "facing", (byte) 1 );

        // Read in items
        List<Object> itemList = compound.getList( "Items", false );
        if ( itemList == null ) return;

        for ( Object item : itemList ) {
            NBTTagCompound itemCompound = (NBTTagCompound) item;

            io.gomint.server.inventory.item.ItemStack itemStack = getItemStack( itemCompound );
            if ( itemStack instanceof ItemAir ) {
                continue;
            }

            byte slot = itemCompound.getByte( "Slot", (byte) 127 );
            if ( slot == 127 ) {
                LOGGER.warn( "Found item without slot information: " + itemStack.getMaterial() + " @ " + this.getBlock().getLocation() + " setting it to the next free slot" );
                this.inventory.addItem( itemStack );
            } else {
                this.inventory.setItem( slot, itemStack );
            }
        }
    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            ( (EntityPlayer) entity ).openInventory( this.inventory );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "ShulkerBox" );
        compound.addValue( "facing", this.facing );
        compound.addValue( "isUndyed", (byte) ( ( this.undyed ) ? 1 : 0 ) );

        if ( reason == SerializationReason.PERSIST ) {
            // Serialize items
            List<NBTTagCompound> nbtTagCompounds = new ArrayList<>();
            for ( int i = 0; i < this.inventory.size(); i++ ) {
                ItemStack itemStack = this.inventory.getItem( i );
                if ( itemStack != null ) {
                    NBTTagCompound nbtTagCompound = new NBTTagCompound( "" );
                    nbtTagCompound.addValue( "Slot", (byte) i );
                    putItemStack( (io.gomint.server.inventory.item.ItemStack) itemStack, nbtTagCompound );
                    nbtTagCompounds.add( nbtTagCompound );
                }
            }

            compound.addValue( "Items", nbtTagCompounds );
        }
    }
}
