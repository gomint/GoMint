package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 311 )
public class ItemDiamondChestplate extends ItemArmor implements io.gomint.inventory.item.ItemDiamondChestplate {

    // CHECKSTYLE:OFF
    public ItemDiamondChestplate( short data, int amount ) {
        super( 311, data, amount );
    }

    public ItemDiamondChestplate( short data, int amount, NBTTagCompound nbt ) {
        super( 311, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getReductionValue() {
        return 8;
    }

    @Override
    public boolean interact( EntityPlayer entity, BlockFace face, Vector clickPosition, Block clickedBlock ) {
        if ( clickedBlock == null ) {
            if ( isBetter( (ItemStack) entity.getArmorInventory().getChestplate() ) ) {
                ItemStack old = (ItemStack) entity.getArmorInventory().getChestplate();
                entity.getArmorInventory().setChestplate( this );
                entity.getInventory().setItem( entity.getInventory().getItemInHandSlot(), old );
            }
        }

        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.DIAMOND_CHESTPLATE;
    }

}
