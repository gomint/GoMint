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
@RegisterInfo( sId = "minecraft:leather_leggings", id = 300 )
public class ItemLeatherLeggings extends ItemLeatherArmor<io.gomint.inventory.item.ItemLeatherLeggings> implements io.gomint.inventory.item.ItemLeatherLeggings {

    @Override
    public float getReductionValue() {
        return 2;
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
        return ItemType.LEATHER_LEGGINGS;
    }

}
