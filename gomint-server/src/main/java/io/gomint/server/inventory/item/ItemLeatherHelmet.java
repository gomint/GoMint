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
@RegisterInfo( sId = "minecraft:leather_helmet", id = 298 )
public class ItemLeatherHelmet extends ItemLeatherArmor<io.gomint.inventory.item.ItemLeatherHelmet> implements io.gomint.inventory.item.ItemLeatherHelmet {

    @Override
    public float getReductionValue() {
        return 1;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            if ( isBetter( (ItemStack<?>) entity.armorInventory().helmet() ) ) {
                ItemStack<?> old = (ItemStack<?>) entity.armorInventory().helmet();
                entity.armorInventory().helmet( this );
                entity.inventory().item( entity.inventory().itemInHandSlot(), old );
            }
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.LEATHER_HELMET;
    }

}
