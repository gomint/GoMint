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
@RegisterInfo( sId = "minecraft:leather_chestplate", id = 299 )
public class ItemLeatherChestplate extends ItemLeatherArmor<io.gomint.inventory.item.ItemLeatherChestplate> implements io.gomint.inventory.item.ItemLeatherChestplate {

    @Override
    public float getReductionValue() {
        return 3;
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
        return ItemType.LEATHER_CHESTPLATE;
    }

}
