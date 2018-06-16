package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnchantTableTileEntity extends ContainerTileEntity implements InventoryHolder {

    /**
     * Create a new enchant table
     *
     * @param position of the enchant table
     */
    public EnchantTableTileEntity( Location position ) {
        super( position );
    }

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public EnchantTableTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );
    }

    @Override
    public void interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) entity;
            player.setEnchantmentInputInventory( new EnchantmentTableInventory( this ) );
            player.openInventory( player.getEnchantmentInputInventory() );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "EnchantTable" );
    }
}
