package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:netherite_leggings", id = 750 )
public class ItemNetheriteLeggings extends ItemNetheriteArmor<io.gomint.inventory.item.ItemNetheriteLeggings> implements io.gomint.inventory.item.ItemNetheriteLeggings {

    @Override
    public float getReductionValue() {
        return 6;
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
        return ItemType.NETHERITE_LEGGINGS;
    }
}
