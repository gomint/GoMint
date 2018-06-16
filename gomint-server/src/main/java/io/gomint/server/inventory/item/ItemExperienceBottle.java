package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntityExpBottle;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 384 )
public class ItemExperienceBottle extends ItemStack implements io.gomint.inventory.item.ItemExperienceBottle {

    // CHECKSTYLE:OFF
    public ItemExperienceBottle( short data, int amount ) {
        super( 384, data, amount );
    }

    public ItemExperienceBottle( short data, int amount, NBTTagCompound nbt ) {
        super( 384, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            EntityExpBottle expBottle = new EntityExpBottle( entity, entity.getWorld() );
            ProjectileLaunchEvent event = new ProjectileLaunchEvent( expBottle, ProjectileLaunchEvent.Cause.THROWING_EXP_BOTTLE );
            entity.getWorld().getServer().getPluginManager().callEvent( event );

            if ( !event.isCancelled() ) {
                entity.getWorld().spawnEntityAt( expBottle, expBottle.getPositionX(), expBottle.getPositionY(), expBottle.getPositionZ(), expBottle.getYaw(), expBottle.getPitch() );

                if ( this.afterPlacement() ) {
                    entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
                } else {
                    entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), this );
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.EXPERIENCE_BOTTLE;
    }

}
