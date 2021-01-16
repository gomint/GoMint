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
@RegisterInfo( sId = "minecraft:golden_leggings", id = 316 )
public class ItemGoldenLeggings extends ItemGoldenArmor<io.gomint.inventory.item.ItemGoldenLeggings> implements io.gomint.inventory.item.ItemGoldenLeggings {

    @Override
    public float getReductionValue() {
        return 3;
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
        return ItemType.GOLDEN_LEGGINGS;
    }

}
