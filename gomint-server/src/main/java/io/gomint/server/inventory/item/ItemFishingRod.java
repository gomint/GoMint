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

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@CanBeDamaged
@RegisterInfo( sId = "minecraft:fishing_rod", id = 346 )
public class ItemFishingRod extends ItemStack< io.gomint.inventory.item.ItemFishingRod> implements io.gomint.inventory.item.ItemFishingRod {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(10000);
    }

    @Override
    public short maxDamage() {
        return 360;
    }

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( entity.getFishingHook() == null ) {
            EntityFishingHook hook = new EntityFishingHook( entity, entity.getWorld() );
            ProjectileLaunchEvent event = new ProjectileLaunchEvent( hook, ProjectileLaunchEvent.Cause.FISHING_ROD );
            entity.getWorld().getServer().pluginManager().callEvent( event );

            if ( !event.cancelled() ) {
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
            this.data( (short) ( this.data() + damage ) );
            entity.setFishingHook( null );
            this.calculateUsageAndUpdate( damage );
        }
    }

    @Override
    public ItemType itemType() {
        return ItemType.FISHING_ROD;
    }

    @Override
    public int enchantAbility() {
        return 1;
    }

}
