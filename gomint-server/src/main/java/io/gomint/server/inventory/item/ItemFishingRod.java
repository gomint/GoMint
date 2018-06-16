package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntityFishingHook;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 346 )
public class ItemFishingRod extends ItemStack implements io.gomint.inventory.item.ItemFishingRod {

    // CHECKSTYLE:OFF
    public ItemFishingRod( short data, int amount ) {
        super( 346, data, amount );
    }

    public ItemFishingRod( short data, int amount, NBTTagCompound nbt ) {
        super( 346, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public short getMaxDamage() {
        return 360;
    }

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public boolean useDamageAsData() {
        return false;
    }

    @Override
    public boolean usesDamage() {
        return true;
    }

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if ( entity.getFishingHook() == null ) {
            EntityFishingHook hook = new EntityFishingHook( entity, entity.getWorld() );
            ProjectileLaunchEvent event = new ProjectileLaunchEvent( hook, ProjectileLaunchEvent.Cause.FISHING_ROD );
            entity.getWorld().getServer().getPluginManager().callEvent( event );

            if ( !event.isCancelled() ) {
                entity.getWorld().spawnEntityAt( hook, hook.getPositionX(), hook.getPositionY(), hook.getPositionZ(), hook.getYaw(), hook.getPitch() );
                entity.setFishingHook( hook );
            }
        } else {
            int damage = entity.getFishingHook().retract();
            entity.setFishingHook( null );

            if ( this.damage( damage ) ) {
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
            } else {
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), this );
            }
        }

        return true;
    }

    @Override
    public void removeFromHand( EntityPlayer entity ) {
        if ( entity.getFishingHook() != null ) {
            int damage = entity.getFishingHook().retract();
            this.setData( (short) ( this.getData() + damage ) );
            entity.setFishingHook( null );

            if ( this.damage( damage ) ) {
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
            } else {
                entity.getInventory().sendContents( entity.getInventory().getItemInHandSlot(), entity.getConnection() );
            }
        }
    }

    @Override
    public ItemType getType() {
        return ItemType.FISHING_ROD;
    }

}
