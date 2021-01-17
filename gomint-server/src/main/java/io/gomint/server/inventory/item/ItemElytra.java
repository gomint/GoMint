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
@RegisterInfo( sId = "minecraft:elytra" )
public class ItemElytra extends ItemStack< io.gomint.inventory.item.ItemElytra> implements io.gomint.inventory.item.ItemElytra {



    @Override
    public ItemType itemType() {
        return ItemType.ELYTRA;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            ItemStack<?> old = (ItemStack<?>) entity.armorInventory().chestplate();
            entity.armorInventory().chestplate( this );
            entity.inventory().item( entity.inventory().itemInHandSlot(), old );
        }

        return false;
    }

}
