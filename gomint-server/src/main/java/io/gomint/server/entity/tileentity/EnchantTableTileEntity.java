package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnchantTableTileEntity extends ContainerTileEntity implements InventoryHolder {

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    public EnchantTableTileEntity( Block block ) {
        super( block );
    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        // Open the chest inventory for the entity
        if ( entity instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) entity;
            player.setEnchantmentInputInventory( new EnchantmentTableInventory( this ) );
            player.openInventory( player.getEnchantmentInputInventory() );
        }
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "EnchantTable" );
    }
}
