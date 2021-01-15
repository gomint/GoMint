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
@RegisterInfo( sId = "minecraft:iron_leggings", id = 308 )
public class ItemIronLeggings extends ItemIronArmor<io.gomint.inventory.item.ItemIronLeggings> implements io.gomint.inventory.item.ItemIronLeggings {

    @Override
    public float getReductionValue() {
        return 5;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            if ( isBetter( (ItemStack<?>) entity.armorInventory().leggings() ) ) {
                ItemStack<?> old = (ItemStack<?>) entity.armorInventory().leggings();
                entity.armorInventory().leggings( this );
                entity.inventory().item( entity.inventory().itemInHandSlot(), old );
            }
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.IRON_LEGGINGS;
    }

}
