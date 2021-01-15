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
@RegisterInfo( sId = "minecraft:chainmail_chestplate", id = 303 )
public class ItemChainChestplate extends ItemChainArmor<io.gomint.inventory.item.ItemChainChestplate> implements io.gomint.inventory.item.ItemChainChestplate {


    @Override
    public float getReductionValue() {
        return 5;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            if ( isBetter( (ItemStack<?>) entity.getArmorInventory().chestplate() ) ) {
                ItemStack<?> old = (ItemStack<?>) entity.getArmorInventory().chestplate();
                entity.getArmorInventory().chestplate( this );
                entity.getInventory().item( entity.getInventory().itemInHandSlot(), old );
            }
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.CHAIN_CHESTPLATE;
    }

}
