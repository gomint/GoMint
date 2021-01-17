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
@RegisterInfo( sId = "minecraft:experience_bottle" )
public class ItemExperienceBottle extends ItemStack< io.gomint.inventory.item.ItemExperienceBottle> implements io.gomint.inventory.item.ItemExperienceBottle {

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            EntityExpBottle expBottle = new EntityExpBottle( entity, entity.world() );
            ProjectileLaunchEvent event = new ProjectileLaunchEvent( expBottle, ProjectileLaunchEvent.Cause.THROWING_EXP_BOTTLE );
            entity.world().getServer().pluginManager().callEvent( event );

            if ( !event.cancelled() ) {
                entity.world().spawnEntityAt( expBottle, expBottle.positionX(), expBottle.positionY(), expBottle.positionZ(), expBottle.yaw(), expBottle.pitch() );
                this.afterPlacement();
            }

            return true;
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.EXPERIENCE_BOTTLE;
    }

}
