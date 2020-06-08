package io.gomint.server.inventory.item;
import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.event.entity.projectile.ProjectileLaunchEvent.Cause;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntitySnowball;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Gamemode;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author Clockw1seLrd
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 332 )
public class ItemSnowball extends ItemStack implements io.gomint.inventory.item.ItemSnowball {

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        // Spawn snowball projectile
        WorldAdapter world = entity.getWorld();
        EntitySnowball snowball = new EntitySnowball( entity, world );

        ProjectileLaunchEvent launchEvent = new ProjectileLaunchEvent( snowball, Cause.THROWING_SNOWBALL );
        world.getServer().getPluginManager().callEvent( launchEvent );

        if ( !launchEvent.isCancelled() ) {
            world.spawnEntityAt( snowball,
                                snowball.getPositionX(),
                                snowball.getPositionY(),
                                snowball.getPositionZ(),
                                snowball.getYaw(),
                                snowball.getPitch() );
        }

        if ( entity.getGamemode() != Gamemode.CREATIVE ) {
            // Subtract amount
            int newAmount = this.getAmount() - 1;
            if ( newAmount == 0 ) {
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
            } else {
                this.setAmount( newAmount );
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), this );
            }
        }

        return true;
    }

    @Override
    public ItemType getType() {
        return ItemType.SNOWBALL;
    }

    @Override
    public byte getMaximumAmount() {
        return 16;
    }
}
