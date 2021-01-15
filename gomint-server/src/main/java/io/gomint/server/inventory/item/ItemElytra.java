package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:elytra", id = 444 )
public class ItemElytra extends ItemStack< io.gomint.inventory.item.ItemElytra> implements io.gomint.inventory.item.ItemElytra {



    @Override
    public ItemType itemType() {
        return ItemType.ELYTRA;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            ItemStack<?> old = (ItemStack<?>) entity.getArmorInventory().chestplate();
            entity.getArmorInventory().chestplate( this );
            entity.getInventory().item( entity.getInventory().itemInHandSlot(), old );
        }

        return false;
    }

}
