package io.gomint.server.inventory.item;
import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntityEnderpearl;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ender_pearl", id = 368 )
public class ItemEnderPearl extends ItemStack< io.gomint.inventory.item.ItemEnderPearl> implements io.gomint.inventory.item.ItemEnderPearl {



    @Override
    public ItemType itemType() {
        return ItemType.ENDER_PEARL;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        // Spawn ender pearl
        EntityEnderpearl entityEnderpearl = new EntityEnderpearl( entity, entity.getWorld() );
        ProjectileLaunchEvent event = new ProjectileLaunchEvent( entityEnderpearl, ProjectileLaunchEvent.Cause.THROWING_ENDER_PEARL );
        entity.getWorld().getServer().pluginManager().callEvent( event );

        if ( !event.isCancelled() ) {
            entity.getWorld().spawnEntityAt( entityEnderpearl, entityEnderpearl.getPositionX(), entityEnderpearl.getPositionY(),
                entityEnderpearl.getPositionZ(), entityEnderpearl.getYaw(), entityEnderpearl.getPitch() );
        }

        // Subtract amount
        int newAmount = this.amount() - 1;
        if ( newAmount == 0 ) {
            entity.getInventory().item( entity.getInventory().itemInHandSlot(), ItemAir.create( 0 ) );
        } else {
            this.amount( newAmount );
            entity.getInventory().item( entity.getInventory().itemInHandSlot(), this );
        }

        return true;
    }

    @Override
    public byte maximumAmount() {
        return 16;
    }

}
