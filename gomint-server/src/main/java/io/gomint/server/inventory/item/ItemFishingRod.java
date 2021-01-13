package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntityFishingHook;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@CanBeDamaged
@RegisterInfo( sId = "minecraft:fishing_rod", id = 346 )
public class ItemFishingRod extends ItemStack implements io.gomint.inventory.item.ItemFishingRod {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public short getMaxDamage() {
        return 360;
    }

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( entity.getFishingHook() == null ) {
            EntityFishingHook hook = new EntityFishingHook( entity, entity.getWorld() );
            ProjectileLaunchEvent event = new ProjectileLaunchEvent( hook, ProjectileLaunchEvent.Cause.FISHING_ROD );
            entity.getWorld().getServer().pluginManager().callEvent( event );

            if ( !event.isCancelled() ) {
                entity.getWorld().spawnEntityAt( hook, hook.getPositionX(), hook.getPositionY(), hook.getPositionZ(), hook.getYaw(), hook.getPitch() );
                entity.setFishingHook( hook );
            }
        } else {
            int damage = entity.getFishingHook().retract();
            entity.setFishingHook( null );

            this.calculateUsageAndUpdate( damage );
        }

        return true;
    }

    @Override
    public void removeFromHand( EntityPlayer entity ) {
        if ( entity.getFishingHook() != null ) {
            int damage = entity.getFishingHook().retract();
            this.setData( (short) ( this.getData() + damage ) );
            entity.setFishingHook( null );
            this.calculateUsageAndUpdate( damage );
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.FISHING_ROD;
    }

    @Override
    public int getEnchantAbility() {
        return 1;
    }

}
