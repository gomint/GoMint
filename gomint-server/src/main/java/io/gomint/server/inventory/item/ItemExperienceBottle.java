package io.gomint.server.inventory.item;
import io.gomint.event.entity.projectile.ProjectileLaunchEvent;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.projectile.EntityExpBottle;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:experience_bottle", id = 384 )
public class ItemExperienceBottle extends ItemStack implements io.gomint.inventory.item.ItemExperienceBottle {

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            EntityExpBottle expBottle = new EntityExpBottle( entity, entity.getWorld() );
            ProjectileLaunchEvent event = new ProjectileLaunchEvent( expBottle, ProjectileLaunchEvent.Cause.THROWING_EXP_BOTTLE );
            entity.getWorld().getServer().getPluginManager().callEvent( event );

            if ( !event.isCancelled() ) {
                entity.getWorld().spawnEntityAt( expBottle, expBottle.getPositionX(), expBottle.getPositionY(), expBottle.getPositionZ(), expBottle.getYaw(), expBottle.getPitch() );
                this.afterPlacement();
            }

            return true;
        }

        return false;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.EXPERIENCE_BOTTLE;
    }

}
