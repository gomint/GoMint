package io.gomint.server.inventory.item;

import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntityEnderpearl;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 368 )
public class ItemEnderPearl extends ItemStack implements io.gomint.inventory.item.ItemEnderPearl {

    // CHECKSTYLE:OFF
    public ItemEnderPearl( short data, int amount ) {
        super( 368, data, amount );
    }

    public ItemEnderPearl( short data, int amount, NBTTagCompound nbt ) {
        super( 368, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ENDER_PEARL;
    }

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        // Spawn ender pearl
        EntityEnderpearl entityEnderpearl = new EntityEnderpearl( entity, entity.getWorld() );
        ProjectileLaunchEvent event = new ProjectileLaunchEvent( entityEnderpearl, ProjectileLaunchEvent.Cause.THROWING_ENDER_PEARL );
        entity.getWorld().getServer().getPluginManager().callEvent( event );

        if ( !event.isCancelled() ) {
            entity.getWorld().spawnEntityAt( entityEnderpearl, entityEnderpearl.getPositionX(), entityEnderpearl.getPositionY(),
                entityEnderpearl.getPositionZ(), entityEnderpearl.getYaw(), entityEnderpearl.getPitch() );
        }

        // Subtract amount
        int newAmount = this.getAmount() - 1;
        if ( newAmount == 0 ) {
            entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
        } else {
            this.setAmount( newAmount );
            entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), this );
        }

        return true;
    }

    @Override
    public byte getMaximumAmount() {
        return 16;
    }

}
